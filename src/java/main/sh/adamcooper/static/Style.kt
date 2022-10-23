package sh.adamcooper.static

import io.ktor.http.ContentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondText
import kotlinx.css.Align
import kotlinx.css.BorderStyle
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.Float
import kotlinx.css.FontStyle
import kotlinx.css.FontWeight.Companion.bold
import kotlinx.css.JustifyContent
import kotlinx.css.LinearDimension
import kotlinx.css.Position
import kotlinx.css.TextAlign
import kotlinx.css.Visibility
import kotlinx.css.WordWrap
import kotlinx.css.a
import kotlinx.css.alignSelf
import kotlinx.css.animationDuration
import kotlinx.css.animationName
import kotlinx.css.backgroundColor
import kotlinx.css.body
import kotlinx.css.borderBottomColor
import kotlinx.css.borderBottomStyle
import kotlinx.css.borderBottomWidth
import kotlinx.css.borderColor
import kotlinx.css.borderRadius
import kotlinx.css.borderStyle
import kotlinx.css.borderWidth
import kotlinx.css.boxShadow
import kotlinx.css.button
import kotlinx.css.color
import kotlinx.css.columnGap
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.float
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.fontStyle
import kotlinx.css.fontWeight
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.lineHeight
import kotlinx.css.margin
import kotlinx.css.marginLeft
import kotlinx.css.marginRight
import kotlinx.css.outlineColor
import kotlinx.css.outlineWidth
import kotlinx.css.padding
import kotlinx.css.paddingBottom
import kotlinx.css.paddingLeft
import kotlinx.css.paddingRight
import kotlinx.css.paddingTop
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.Animations
import kotlinx.css.properties.BoxShadows
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.border
import kotlinx.css.properties.lh
import kotlinx.css.properties.s
import kotlinx.css.properties.scale
import kotlinx.css.properties.transform
import kotlinx.css.px
import kotlinx.css.textAlign
import kotlinx.css.textDecoration
import kotlinx.css.top
import kotlinx.css.visibility
import kotlinx.css.vw
import kotlinx.css.width
import kotlinx.css.wordWrap
import kotlinx.css.zIndex

private val COLOR_BACKGROUND = Color("#29353B")
private val COLOR_BACKGROUND_DARK = Color("#263238")
private val COLOR_BAR = Color("#7F8C8E")
private val COLOR_PLAIN_TEXT = Color("#CAD8D7")
private val COLOR_OUTLINE = Color("#212C31")

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}

fun CssBuilder.globalStyle() {
    body {
        backgroundColor = COLOR_BACKGROUND
        fontFamily = "Roboto, sans-serif"
    }

    a {
        textDecoration = TextDecoration.none
        color = COLOR_PLAIN_TEXT
        fontWeight = bold
    }

    button {
        border(0.px, BorderStyle.none, COLOR_BACKGROUND)
        boxShadow = BoxShadows.none
    }

    "::-webkit-scrollbar" {
        width = 8.px
    }

    "::-webkit-scrollbar-track" {
        backgroundColor = COLOR_BACKGROUND
    }

    "::-webkit-scrollbar-thumb" {
        backgroundColor = Color("#222D32")
    }

    "::-webkit-scrollbar-thumb:hover" {
        backgroundColor = COLOR_BAR
    }

    ".navbar" {
        padding = 0.toString()
    }

    ".navbar header" {
        fontFamily = "'Roboto Medium', sans-serif"
        paddingBottom = 20.px
        textAlign = TextAlign.center
        position = Position.fixed
        top = 0.pct
        zIndex = 10
        width = 100.pct
    }

    ".navbar a" {
        backgroundColor = COLOR_BACKGROUND_DARK
        float = Float.left
        textAlign = TextAlign.center
        width = 20.pct
        color = COLOR_BAR
        outlineWidth = 1.px
        put("outline-style", "solid")
        outlineColor = COLOR_OUTLINE
        margin = (-2).px.value
        padding = "15px 0"
        animationName = "navbar-hover-fade-out"
        animationDuration = .25.s
    }

    ".navbar a.selected" {
        animationName = Animations.none.toString()
        borderBottomColor = Color("#00BCD4")
        borderBottomWidth = 2.px
        borderBottomStyle = BorderStyle.solid
        marginRight = 3.px
        backgroundColor = COLOR_BACKGROUND
        color = COLOR_PLAIN_TEXT
    }

    ".navbar a.selected:hover" {
        animationName = Animations.none.toString()
    }

    ".navbar a:hover" {
        backgroundColor = COLOR_BACKGROUND
        color = COLOR_PLAIN_TEXT
        animationName = "navbar-hover-fade-in"
        animationDuration = .25.s
    }

    "@keyframes navbar-hover-fade-out" {
        "from" {
            backgroundColor = COLOR_BACKGROUND
            color = COLOR_PLAIN_TEXT
        }
        "to" {
            backgroundColor = COLOR_BACKGROUND_DARK
            color = COLOR_BAR
        }
    }

    "@keyframes navbar-hover-fade-in" {
        "from" {
            backgroundColor = COLOR_BACKGROUND_DARK
            color = COLOR_BAR
        }
        "to" {
            backgroundColor = COLOR_BACKGROUND
            color = COLOR_PLAIN_TEXT
        }
    }

    "#nameplate" {
        fontFamily = "'Roboto Thin', 'Roboto Light', sans-serif"
        color = Color.white
        textAlign = TextAlign.center
        width = 100.pct
        fontSize = 4.vw
    }

    "#findme" {
        paddingLeft = 25.pct
        paddingRight = 25.pct
    }

    "#findme a" {
        float = Float.left
        textAlign = TextAlign.center
        width = 33.pct
    }

    ".text p" {
        paddingTop = 80.px
        color = COLOR_PLAIN_TEXT
        zIndex = 10000
        wordWrap = WordWrap.breakWord
        lineHeight = 200.pct.lh
        textAlign = TextAlign.justify
    }

    ".text p#aboutme" {
        margin = 80.px.value
    }

    ".introduction h3" {
        paddingTop = 80.px
        textAlign = TextAlign.center
        color = COLOR_PLAIN_TEXT
    }

    "#q-and-a" {
        margin = 80.px.value
    }

    ".question" {
        color = COLOR_PLAIN_TEXT
        backgroundColor = COLOR_BACKGROUND_DARK
        fontFamily = "'Roboto Light', sans-serif"
        fontStyle = FontStyle.italic
        fontSize = 20.px
        padding = 20.px.value
        borderWidth = 1.px
        borderColor = COLOR_OUTLINE
        borderStyle = BorderStyle.solid
        borderRadius = 10.px
    }

    ".answer" {
        color = COLOR_PLAIN_TEXT
        paddingTop = 20.px
        paddingBottom = 20.px
        paddingLeft = 20.px
        fontSize = 16.px
        put("font-align", "justify")
        lineHeight = 200.pct.lh
    }

    "#email" {
        paddingTop = 60.px
        display = Display.block
        marginLeft = LinearDimension.auto
        marginRight = LinearDimension.auto
    }

    "#gh-repo-link" {
        margin = LinearDimension.auto.value
        width = 50.pct
        textAlign = TextAlign.center
        paddingTop = 15.pct
    }

    ".app" {
        textAlign = TextAlign.center
    }

    ".app h1" {
        fontFamily = "'Roboto Thin', 'Roboto Light', sans-serif"
        color = Color.white
        width = 100.pct
        fontSize = 4.vw
    }

    ".appIntroduction" {
        color = COLOR_PLAIN_TEXT
        textAlign = TextAlign.center
        paddingLeft = 10.pct
        paddingRight = 10.pct
    }

    ".appGitHubLink" {
        alignSelf = Align.center
    }

    ".appGitHubLogo" {
        width = 40.px
        height = 40.px
        alignSelf = Align.center
    }

    ".wordleTableContainer" {
        paddingTop = 5.pct
    }

    ".wordleTable, .wordleTable tr, .wordleTable th, .wordleTable td" {
        color = COLOR_PLAIN_TEXT
        marginLeft = LinearDimension.auto
        marginRight = LinearDimension.auto
        border(1.px, BorderStyle.solid, COLOR_PLAIN_TEXT)
        textAlign = TextAlign.center
    }

    ".wordleTable th, .wordleTable td" {
        paddingLeft = 2.em
        paddingRight = 2.em
    }

    ".wordleCopyButtonContainer" {
        width = 4.em
        height = width
    }

    ".wordleCopyButton" {
        color = COLOR_PLAIN_TEXT
        backgroundColor = COLOR_BACKGROUND
        marginLeft = LinearDimension.auto
        marginRight = LinearDimension.auto
        transform { scale(2) }
        visibility = Visibility.hidden
    }

    ".wordleResultsContainer" {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.spaceAround
    }

    ".wordleFormattedResultRow" {
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.spaceBetween
        paddingTop = 3.px
        paddingBottom = 3.px
        columnGap = 3.px
    }

    ".wordleFormattedResultRow div" {
        border(2.px, BorderStyle.solid, COLOR_PLAIN_TEXT)
        padding(5.px)
        width = 12.px
    }

    ".wordleFormattedResultGreen" {
        backgroundColor = Color.darkGreen
    }

    ".wordleFormattedResultYellow" {
        backgroundColor = Color.darkGoldenrod
    }

    ".wordleFormattedResultBlack" {
        backgroundColor = Color.darkSlateGray
    }
}
