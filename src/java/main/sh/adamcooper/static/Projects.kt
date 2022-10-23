package sh.adamcooper.static

import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.title
import sh.adamcooper.apps.wordle.wordleDescription

fun HTML.projects() {
    head {
        meta(charset = "UTF-8")
        title("Adam Cooper - Projects")
        link(rel = "stylesheet", type = "text/css", href = "/static/style.css")
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
                a(href = "/projects", classes = "selected") { +"Projects" }
                a(href = "/") { +"Home" }
                a(href = "/opinions") { +"Opinions" }
                a(href = "/contact") { +"Contact" }
            }
        }

        div(classes = "introduction") {
            h3 { +"These are some projects I have worked on over the years." }
        }

        div(classes = "app") {
            h1 {
                a(href = "/projects/wordle") { +"Wordle Solver" }
            }
            a(href = "https://github.com/super-cooper/wordle", target = "_blank", classes = "appGitHubLink") {
                img(src = "/static/img/social/github.svg", alt = "GitHub logo", classes = "appGitHubLogo")
            }
            h3(classes = "appIntroduction") { wordleDescription() }
        }

        div(classes = "text") {
            id = "gh-repo-link"
            a(
                href = "https://github.com/super-cooper?tab=repositories",
                target = "_blank"
            ) {
                +"My GitHub Repositories"
            }
        }
    }
}
