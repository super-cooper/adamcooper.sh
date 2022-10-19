package sh.adamcooper.static

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.title
import sh.adamcooper.infrastructure.LOCAL_TIME_ZONE

private val BIRTHDAY = LocalDateTime(
    year = 1996,
    month = Month.OCTOBER,
    dayOfMonth = 8,
    hour = 0,
    minute = 9
).toInstant(LOCAL_TIME_ZONE)

fun HTML.about() {
    val age = BIRTHDAY.periodUntil(Clock.System.now(), LOCAL_TIME_ZONE).years
    head {
        meta(charset = "UTF-8")
        title("About Me - Adam Cooper")
        link(rel = "stylesheet", type = "text/css", href = "/static/style.css")
        link(rel = "shortcut icon", href = "/static/img/lambda.png")
        // Fonts
        link(
            href = "https://fonts.googleapis.com/css?family=Roboto&display=swap",
            rel = "stylesheet"
        )
    }

    body {
        // Top bar of site with links to other pages TODO template
        div(classes = "navbar") {
            header {
                a(href = "/about", classes = "selected") { +"About Me" }
                a(href = "/projects") { +"Projects" }
                a(href = "/") { +"Home" }
                a(href = "/opinions") { +"Opinions" }
                a(href = "/contact") { +"Contact" }
            }
        }
        div(classes = "text") {
            p {
                id = "aboutme"
                +"""
                    I am Adam Cooper, a $age year old computer programmer from New Jersey. 
                    I program mostly in Kotlin and Python. In my free time, I like to work on 
                    personal projects (both programming and non-programming, but mostly 
                    technology-related), play video games, watch baseball, make/play music, 
                    watch movies, and cook! Feel free to contact me if you want to talk about any 
                    of these things, as long as you're not trying to sell me anything ;) 
                """.trimIndent()
                br {}
                br {}
                +"""
                    I try to keep more specific and/or personal information off of the 
                    internet, but if you want to know more about my interests, feel 
                    free to visit the 
                """.trimIndent()
                a(href = "/opinions") { +"opinions" }
                +" page of my website."
            }
        }
    }
}
