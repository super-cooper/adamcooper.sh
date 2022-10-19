package sh.adamcooper.apps.wordle

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.transactions.transaction
import sh.adamcooper.infrastructure.DB
import sh.adamcooper.infrastructure.LOCAL_TIME_ZONE
import java.time.Month

/**
 * Manages IO for the Wordle database
 */
internal object WordleDB {
    private val db = DB.connect("wordle")

    private val WORDLE_FIRST_DATE = LocalDate(year = 2021, month = Month.JUNE, dayOfMonth = 19)

    val solutions: Sequence<Solution>
        get() = transaction(this.db) { Solution.all().toList().reversed() }.asSequence()

    private val nextDate: LocalDate
        get() = (this.solutions.mostRecent?.publishedOn ?: WORDLE_FIRST_DATE) + DatePeriod(days = 1)

    val Sequence<Solution>.mostRecent: Solution?
        get() = this.sortedByDescending(Solution::id).firstOrNull()

    /**
     * Save the next Wordle solution to the database
     */
    @Suppress("MagicNumber")
    fun saveSolution(guesses: List<String>, theAnswer: String): Solution = transaction(this.db) {
        Solution.new {
            publishedOn = WordleDB.nextDate
            solvedOn = Clock.System.todayIn(LOCAL_TIME_ZONE)
            answer = theAnswer
            guess1 = guesses[0]
            guess2 = guesses.getOrNull(1)
            guess3 = guesses.getOrNull(2)
            guess4 = guesses.getOrNull(3)
            guess5 = guesses.getOrNull(4)
            guess6 = guesses.getOrNull(5)
        }
    }

    /**
     * The table for Wordle solutions
     */
    private object Solutions : IntIdTable() {
        // The date on which the puzzle was originally published by Wordle
        val publishedOn = date("published_on").uniqueIndex()

        // The date on which the puzzle was solved by this Wordle app
        val solvedOn = date("solved_on")

        // The final answer of the puzzle. Required in case we failed to guess correctly
        val answer = char("answer", length = 5)

        // The guesses (up to 6)
        val guess1 = char("guess_1", length = 5)
        val guess2 = char("guess_2", length = 5).nullable()
        val guess3 = char("guess_3", length = 5).nullable()
        val guess4 = char("guess_4", length = 5).nullable()
        val guess5 = char("guess_5", length = 5).nullable()
        val guess6 = char("guess_6", length = 5).nullable()

        init {
            transaction(db) {
                SchemaUtils.create(Solutions)
            }
        }
    }

    /**
     * Represents one row in the Solutions table
     */
    internal class Solution(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Solution>(Solutions)

        var publishedOn by Solutions.publishedOn
        var solvedOn by Solutions.solvedOn
        var answer by Solutions.answer
        var guess1 by Solutions.guess1
        var guess2 by Solutions.guess2
        var guess3 by Solutions.guess3
        var guess4 by Solutions.guess4
        var guess5 by Solutions.guess5
        var guess6 by Solutions.guess6
    }
}
