package sh.adamcooper

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.cio.EngineMain
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.staticResources
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

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    install(CallLogging)
    routing {
        trace { this.application.log.trace(it.buildText()) }
        get("/") { this.call.respondHtml(HttpStatusCode.OK, HTML::index) }
        get("/about") { this.call.respondHtml(HttpStatusCode.OK, HTML::about) }
        route("/projects") {
            get { this.call.respondHtml(HttpStatusCode.OK, HTML::projects) }
            get("/wordle") { this.call.respondHtml(HttpStatusCode.OK, HTML::wordleApp) }
        }
        get("/opinions") { this.call.respondHtml(HttpStatusCode.OK, HTML::opinions) }
        get("/contact") { this.call.respondHtml(HttpStatusCode.OK, HTML::contact) }
        route("/static") {
            get("style.css") { this.call.respondCss(CssBuilder::globalStyle) }
            staticResources("/", "static")
        }
        staticResources("/", "text")
    }
}
