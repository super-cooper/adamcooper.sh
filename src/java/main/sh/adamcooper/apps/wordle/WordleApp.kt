package sh.adamcooper.apps.wordle

import io.ktor.http.LinkHeader
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
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.noScript
import kotlinx.html.script
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.title
import kotlinx.html.tr
import sh.adamcooper.apps.wordle.WordleDB.mostRecent
import sh.adamcooper.apps.wordle.WordleState.WORDLE_FIRST_DATE
import sh.adamcooper.wordle.Wordle
import kotlin.time.Duration.Companion.hours

/**
 * Manages the local state of Wordle solutions
 */
private object WordleState {
    val WORDLE_FIRST_DATE = LocalDate(year = 2021, month = Month.JUNE, dayOfMonth = 19)
    private var lastWordListRefresh = Clock.System.now()

    val wordle = Wordle()
        get() {
            if (this.lastWordListRefresh + 1.hours < Clock.System.now()) {
                return Wordle().also {
                    this.lastWordListRefresh = Clock.System.now()
                }
            }
            return field
        }

    val solutions: Sequence<WordleDB.Solution>
        get() {
            return sequence {
                val knownSolutions = WordleDB.solutions
                val lastIndexSolved = knownSolutions.mostRecent?.id?.value ?: 0
                for (index in WordleState.wordle.count downTo lastIndexSolved) {
                    val answer = WordleState.wordle.answer(index)
                    val guesses = WordleState.wordle.play(
                        WordleState.wordle.bestWord,
                        answer
                    )
                    this.yield(WordleDB.saveSolution(guesses, answer))
                }
                this.yieldAll(knownSolutions)
            }
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

private fun TABLE.wordleDisplay(solution: WordleDB.Solution) {
    val wordleNumber = solution.id.value
    tr {
        td { +wordleNumber.toString() }
        td { +(WORDLE_FIRST_DATE + DatePeriod(days = wordleNumber)).toString() }
        td { +solution.answer }
        val solutionID = "solution-$wordleNumber"
        td(classes = "wordleSolutionText") {
            id = solutionID
            +sequenceOf(
                solution.guess1,
                solution.guess2,
                solution.guess3,
                solution.guess4,
                solution.guess5,
                solution.guess6
            ).takeWhile { it != null }.toList().toString()
        }
        td(classes = "wordleCopyButtonContainer") {
            button(classes = "fa fa-copy wordleCopyButton", type = ButtonType.button) {
                this.id = "wordleCopy-$wordleNumber"
            }
            noScript {
                +"TODO this will be the text"
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
                    WordleState.solutions.forEach(this::wordleDisplay)
                }
            }
        }
        script(type = ScriptType.textJavaScript, src = "/static/scripts/adamcooper-sh.js") {}
    }
}
