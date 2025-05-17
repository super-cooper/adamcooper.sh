package sh.adamcooper

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.resource
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.routing.application
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.css.CssBuilder
import kotlinx.html.HTML
import sh.adamcooper.apps.wordle.wordleApp
import sh.adamcooper.static.about
import sh.adamcooper.static.contact
import sh.adamcooper.static.globalStyle
import sh.adamcooper.static.index
import sh.adamcooper.static.opinions
import sh.adamcooper.static.projects
import sh.adamcooper.static.respondCss

/**
 * Definition for the server
 */
fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        install(CallLogging)
        routing {
            trace { this.application.log.trace(it.buildText()) }
            get("/") {
                this.call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            get("/about") {
                this.call.respondHtml(HttpStatusCode.OK, HTML::about)
            }
            route("/projects") {
                get {
                    this.call.respondHtml(HttpStatusCode.OK, HTML::projects)
                }
                get("/wordle") {
                    this.call.respondHtml(HttpStatusCode.OK, HTML::wordleApp)
                }
            }
            get("/opinions") {
                this.call.respondHtml(HttpStatusCode.OK, HTML::opinions)
            }
            get("/contact") {
                this.call.respondHtml(HttpStatusCode.OK, HTML::contact)
            }
            static("/static") {
                get("style.css") {
                    this.call.respondCss(CssBuilder::globalStyle)
                }
                static("img") {
                    resources("img")
                }
                static("scripts") {
                    resource("adamcooper-sh.js")
                    resource("adamcooper-sh.js.map")
                }
            }
            resource("keybase.txt")
        }
    }.start(wait = true)
}
