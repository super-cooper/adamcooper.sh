package sh.adamcooper

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.files
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticRootFolder
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.HTML
import sh.adamcooper.static.index
import java.io.File

fun main() {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1") {
        routing {
            get("/") {
                this.call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            static("/static") {
                static {
                    resources("img")
                }
            }
        }
    }.start(wait = true)
}
