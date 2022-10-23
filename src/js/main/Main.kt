import kotlinx.browser.document
import kotlinx.css.Visibility
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.asList
import util.copyToClipboard

fun main() {
    // Register Wordle copy buttons
    val solutions = document.getElementsByClassName("wordleSolutionPlain")
    val copyButtons = document.getElementsByClassName("wordleCopyButton")
    solutions.asList().zip(copyButtons.asList()).forEach { (solutionElement, buttonElement) ->
        val button = buttonElement as HTMLButtonElement
        button.onclick = { copyToClipboard(solutionElement) }
        button.style.visibility = Visibility.visible.toString()
    }
}
