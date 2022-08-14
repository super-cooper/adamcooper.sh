package sh.adamcooper.static

import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.img
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.title

fun HTML.contact() {
    head {
        meta(charset = "UTF-8")
        title("Contact - Adam Cooper")
        link(rel = "shortcut icon", href = "/static/img/lambda.png")
        // Fonts
        link(
            href = "https://fonts.googleapis.com/css?family=Roboto&display=swap",
            rel = "stylesheet"
        )
    }

    body {
        // Top bar of site with links to other pages
        div(classes = "navbar") {
            header {
                a(href = "/about") { +"About Me" }
                a(href = "/projects") { +"Projects" }
                a(href = "/") { +"Home" }
                a(href = "/opinions") { +"Opinions" }
                a(href = "/contact", classes = "selected") { +"Contact" }
            }
        }

        div(classes = "introduction") {
            h3 {
                +"Please feel free to contact me via email, or DM me on "
                a(href = "https://twitter.com/_c00p", target = "_blank") { +"Twitter" }
                +" or "
                a(
                    href = "https://www.linkedin.com/in/adamcooper-oc",
                    target = "_blank"
                ) {
                    +"LinkedIn"
                }
                +"."
                br { }
                br { }
                +"""
                    My email is an image because of bots that scrape the internet for 
                    addresses to put on spam lists.
                """.trimIndent()
                br { }
                br { }
                +"""
                    Feel free to contact me about questions, this website, just to 
                    chat, or anything really! Please, just don't try to sell me 
                    anything :)
                """.trimIndent()
            }
        }
        img(src = "/static/img/email.jpg") {
            alt = "ah dumb at ah dumb kew per dawt ess aytch"
        }
    }
}
