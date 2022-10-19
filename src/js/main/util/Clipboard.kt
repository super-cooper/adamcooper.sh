package util

import kotlinx.browser.window
import org.w3c.dom.Element

fun copyToClipboard(selector: Element) {
    window.navigator.clipboard.writeText(selector.textContent ?: "")
}
