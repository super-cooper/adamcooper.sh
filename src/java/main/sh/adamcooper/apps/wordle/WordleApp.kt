package sh.adamcooper.apps.wordle

import io.ktor.http.LinkHeader
import io.ktor.util.logging.KtorSimpleLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus
import kotlinx.html.ButtonType
import kotlinx.html.H3
import kotlinx.html.HTML
import kotlinx.html.LinkRel
import kotlinx.html.LinkType
import kotlinx.html.ScriptType
import kotlinx.html.TABLE
import kotlinx.html.TD
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.noScript
import kotlinx.html.script
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.title
import kotlinx.html.tr
import sh.adamcooper.apps.wordle.WordleState.WORDLE_FIRST_DATE
import sh.adamcooper.wordle.Wordle
import kotlin.time.Duration.Companion.hours

private const val WORDLE_BUFFER_CAPACITY = 1000

/**
 * Manages the local state of Wordle solutions
 */
private object WordleState {
    val WORDLE_FIRST_DATE = LocalDate(year = 2021, month = Month.JUNE, dayOfMonth = 19)
    private var lastWordListRefresh = Clock.System.now()
    private val log = KtorSimpleLogger("WordleState")

    val wordle = Wordle()
        get() {
            if (this.lastWordListRefresh + 1.hours < Clock.System.now()) {
                return Wordle().also {
                    this.log.info("Refreshing Wordle state")
                    this.lastWordListRefresh = Clock.System.now()
                }
            }
            return field
        }

    val solutions: Flow<WordleDB.Solution>
        get() = flow {
            val knownSolutions = WordleDB.solutions
            for (id in WordleState.wordle.count downTo knownSolutions.count()) {
                WordleState.log.info("Solving new Wordle puzzle $id")
                val answer = WordleState.wordle.answer(id)
                WordleState.log.info("Resolved Wordle answer $answer")
                val guesses = WordleState.wordle.play(WordleState.wordle.bestWord, answer)
                emit(WordleDB.saveSolution(id, guesses, answer))
            }
            emitAll(knownSolutions.asFlow())
        }
}

internal fun H3.wordleDescription() {
    +"If you're like me, and your family plays "
    a(href = "https://www.nytimes.com/games/wordle", target = "_blank") { +"Wordle" }
    +" every day, and you're really bad at it and it takes too much time, then my Wordle solver "
    +"might help you as it has helped me. It uses a greedy algorithm to select the most valuable "
    +"word for each guess, starting at an initial word and (hopefully) arriving at "
    +"the correct answer."
}

private fun wordleGuessSequence(solution: WordleDB.Solution) = sequenceOf(
    solution.guess1,
    solution.guess2,
    solution.guess3,
    solution.guess4,
    solution.guess5,
    solution.guess6
).filterNotNull()

private fun TD.wordleFormatted(solution: WordleDB.Solution) {
    for (guess in wordleGuessSequence(solution)) {
        val results = WordleState.wordle.getResultOfGuess(guess, solution.answer)
        div(classes = "wordleFormattedResultRow") {
            results.zip(guess.asIterable()).forEach { (result, char) ->
                when (result) {
                    Wordle.Letter.GREEN -> div(classes = "wordleFormattedResultGreen") { +char.toString() }
                    Wordle.Letter.YELLOW -> div(classes = "wordleFormattedResultYellow") { +char.toString() }
                    else -> div(classes = "wordleFormattedResultBlack") { +char.toString() }
                }
            }
        }
    }
}

private fun wordleCopyText(solution: WordleDB.Solution): String = buildString {
    val guesses = wordleGuessSequence(solution)
    val count = solution.guess6?.let { if (it != solution.answer) "X" else null } ?: guesses.count().toString()
    appendLine("Wordle ${solution.id} $count/6")
    appendLine()
    for (guess in guesses) {
        for (letter in WordleState.wordle.getResultOfGuess(guess, solution.answer)) {
            when (letter) {
                Wordle.Letter.GREEN -> append("🟩")
                Wordle.Letter.YELLOW -> append("🟨")
                Wordle.Letter.BLACK -> append("⬛")
            }
        }
        appendLine()
    }
}.trim()

private fun TABLE.wordleDisplay(solution: WordleDB.Solution) {
    val wordleNumber = solution.id.value
    tr {
        td { +wordleNumber.toString() }
        td { +(WORDLE_FIRST_DATE + DatePeriod(days = wordleNumber)).toString() }
        td { +solution.answer }
        td { this.wordleFormatted(solution) }
        td {
            div(classes = "wordleResultsContainer") {
                button(classes = "fa fa-copy wordleCopyButton", type = ButtonType.button)
                noScript(classes = "wordleSolutionPlain") { +wordleCopyText(solution) }
            }
        }
    }
}

fun HTML.wordleApp() {
    head {
        meta(charset = Charsets.UTF_8.name())
        title("Wordle Solver - Adam Cooper")
        link(rel = LinkHeader.Rel.Stylesheet, type = LinkType.textCss, href = "/static/style.css")
        link(rel = "shortcut icon", href = "/static/img/lambda.png")
        // Fonts
        link(
            href = "https://fonts.googleapis.com/css?family=Roboto&display=swap",
            rel = LinkHeader.Rel.Stylesheet
        )
        link(
            href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css",
            rel = LinkRel.stylesheet
        )
    }

    body {
        // Top bar of site with links to other pages TODO template
        div(classes = "navbar") {
            header {
                a(href = "/about") { +"About Me" }
                a(href = "/projects") { +"Projects" }
                a(href = "/") { +"Home" }
                a(href = "/opinions") { +"Opinions" }
                a(href = "/contact") { +"Contact" }
            }
        }

        div(classes = "app") {
            h1 {
                +"Wordle Solver"
            }
            div(classes = "appIntroduction") {
                h3 { this.wordleDescription() }
            }
            div(classes = "wordleTableContainer") {
                table(classes = "wordleTable") {
                    tr {
                        th { +"#" }
                        th { +"Date" }
                        th { +"Answer" }
                        th { +"Solution" }
                        th { +"Copy" }
                    }
                    runBlocking {
                        WordleState.solutions.buffer(WORDLE_BUFFER_CAPACITY).toList()
                            .sortedByDescending(WordleDB.Solution::id).forEach(this@table::wordleDisplay)
                    }
                }
            }
        }
        script(type = ScriptType.textJavaScript, src = "/static/scripts/adamcooper-sh.js") {}
    }
}
