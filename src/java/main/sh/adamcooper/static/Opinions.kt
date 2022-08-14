package sh.adamcooper.static

import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.title

fun HTML.opinions() {
    head {
        meta(charset = "UTF-8")
        title("Adam's Apinions - Adam Cooper")
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
                a(href = "/opinions", classes = "selected") { +"Opinions" }
                a(href = "/contact") { +"Contact" }
            }
        }

        div(classes = "introduction") {
            h3 {
                +"Here are some opinions that I do not hold very closely at all. "
                +"They change often, and I think it's cool if yours "
                +"are different from mine."
            }
        }

        div {
            id = "q-and-a"
            div(classes = "question") {
                +"> What editor do you use?"
            }
            div(classes = "answer") {
                +"I use "
                a(href = "https://www.vim.org", target = "_blank") { +"Vim" }
                +" because it is extremely lightweight, and highly customizable. "
                +"I don't customize that much from the default config, but I love "
                +"having the ability to do so. If you want to, check out my "
                a(
                    href = "https://github.com/super-cooper/my-dotfiles/blob/master/.vimrc",
                    target = "_blank"
                ) {
                    +".vimrc"
                }
                +" or my "
                a(
                    href = "https://github.com/super-cooper/my-dotfiles/tree/master/.vim",
                    target = "_blank"
                ) {
                    +"plugins."
                }
                +" On Windows, I like to use "
                a(href = "https://notepad-plus-plus.org", target = "_blank") {
                    +"Notepad++"
                }
                +" because it is lightweight and opens quickly. For a plain text editor"
                +" on Windows, I rarely need to use it for more than making a few edits"
                +" to a config file here and there. I use Vim mainly on Linux, where"
                +" I am mostly opening text files from the command line."
            }

            div(classes = "question") {
                +"> What IDE do you use?"
            }
            div(classes = "answer") {
                +"I use "
                a(href = "https://www.jetbrains.com", target = "_blank") {
                    +"JetBrains"
                }
                +" IDEs almost exclusively. They are mostly known for making the "
                +"popular Java IDE "
                a(href = "https://www.jetbrains.com/idea", target = "_blank") {
                    +"IntelliJ IDEA"
                }
                +", but if they support the language I'm using, I use their software."
                +" This is a pretty radical opinion, but I think JetBrains makes "
                +"the best software in any context. It is incredibly feature-rich "
                +"without being bloated, and wildly extensible. The only thing I don't "
                +"like about it is that not all of it is free and open source. If the "
                +"language I am using is not supported by JetBrains software, "
                +"I usually just go with Vim and the command line."
            }

            div(classes = "question") {
                +"> What language is your favorite?"
            }
            div(classes = "answer") {
                +"""
                    I enjoy writing in Python the most, since it's very simple and can 
                    be written as either a program or a script. Also I find writing code
                    "pythonically" lends itself to being very elegant. Python has a 
                    number of language features that I enjoy very much, and use 
                    liberally, such as list comprehensions and decorators. I also like 
                    that it is a true multi-paradigm language that takes full 
                    advantage of imperative, object-oriented, and functional 
                    philosophies. What I don't like about Python is that it is 
                    interpreted, duck-typed, and runs on a single thread.
                """.trimIndent()
            }

            div(classes = "question") {
                +"> What language do you dislike the most?"
            }
            div(classes = "answer") {
                +"All of them, including Python."
            }

            div(classes = "question") {
                +"> What operating system do you use?"
            }
            div(classes = "answer") {
                +"For most everyday things like web browsing, programming, answering "
                +"emails, what have you, I use "
                a(href = "https://www.debian.org", target = "_blank") {
                    +"Debian GNU/Linux"
                }
                +". Specifically, I use the "
                a(href = "https://wiki.debian.org/DebianTesting", target = "_blank") {
                    +"testing branch"
                }
                +". I like that it gets almost all of the software support that "
                a(href = "https://ubuntu.com", target = "_blank") { +"Ubuntu" }
                +" gets with a higher degree of freedom. I also like that it's much "
                +"more up to date than the "
                a(href = "https://wiki.debian.org/DebianStable", target = "_blank") {
                    +"stable branch"
                }
                +". I also have a computer that runs "
                a(href = "https://www.microsoft.com/en-us/windows", target = "_blank") {
                    +"Windows 10"
                }
                +" that I use mainly for playing video games, making music, and "
                +"watching Netflix."
            }

            div(classes = "question") {
                +"> What shell do you use?"
            }
            div(classes = "answer") {
                +"I use "
                a(href = "https://www.zsh.org/", target = "_blank") { +"ZSH" }
                +" for regular shell interaction. I decided to switch after years of "
                +"using "
                a(href = "https://www.gnu.org/software/bash/", target = "_blank") {
                    +"BASH"
                }
                +" because it is made with more user-focused customization in mind as "
                +"opposed to complete open-endedness, although you can still take "
                +"advantage of BASH-like customization most of the time if you want. "
                +"I do still use BASH for my shell-scripting though, because I believe "
                +"it works better as a scripting language than ZSH."
            }

            div(classes = "question") {
                +"> Do you only use free software?"
            }
            div(classes = "answer") {
                +"""
                    Not at all, no. I use proprietary software if it's just better than
                    the free alternative, but I try to use free software when I can.
                    I generally prefer the philosophy and user-focused design of most
                    free software, but I understand everyone's gotta make a living 
                    somehow!
                """.trimIndent()
            }

            div(classes = "question") {
                +"> What do you use to make music?"
            }
            div(classes = "answer") {
                +"I use "
                a(href = "https://www.ableton.com/en/live", target = "_blank") {
                    +"Ableton Live"
                }
                +" for mixing, production, and synths, and "
                a(href = "https://www.finalemusic.com/", target = "_blank") {
                    +"Finale"
                }
                +" for engraving. Most of the music I make is bad and can't really be "
                +"categorized by a genre because most of it is just whatever nonsense "
                +"passes through my head."
            }

            div(classes = "question") {
                +"> What do you play games on?"
            }
            div(classes = "answer") {
                +"I play most online games on PC ("
                a(href = "https://store.steampowered.com/", target = "_blank") {
                    +"Steam"
                }
                +"), but I also have a PS4, Nintendo Switch, and Nintendo 3DS."
            }

            div(classes = "question") {
                +"> What was your first game system?"
            }
            div(classes = "answer") {
                +"I had a SEGA Genesis, and I beat Sonic The Hedgehog probably"
                +" hundreds of times."
            }
        }
    }
}
