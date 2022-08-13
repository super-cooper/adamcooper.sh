package sh.adamcooper.static

import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.title

fun HTML.index() {
    head {
        meta(charset = "UTF-8")
        title("Adam Cooper")
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
                a(href = "/about-me") { +"About Me" }
                a(href = "/projects") { +"Projects" }
                a(href = "/", classes = "selected") { +"Home" }
                a(href = "/opinions") { +"Opinions" }
                a(href = "/contact") { +"Contact" }
            }
        }

        // Part of the site that just says my name
        div {
            id = "nameplate"
            h1 { +"Adam Cooper" }
        }

        // Links to other sites I'm on
        div {
            id = "findme"
            a(href = "https://twitter.com/__c00p", target = "_blank") {
                img(src = "/static/img/social/twitter.svg", alt = "Twitter logo") {
                    title = "Twitter"
                    width = "120" // TODO put this all in CSS
                    height = "120"
                }
            }
            a(href = "https://github.com/super-cooper", target = "_blank") {
                img(src = "/static/img/social/github.svg", alt = "GitHub logo") {
                    title = "GitHub"
                    width = "120"
                    height = "120"
                }
            }
            a(href = "https://www.linkedin.com/in/adamcooper-oc/", target = "_blank") {
                img(src = "/static/img/social/linkedin.svg", alt = "LinkedIn logo") {
                    title = "LinkedIn"
                    width = "120"
                    height = "120"
                }
            }
        }
    }
}
