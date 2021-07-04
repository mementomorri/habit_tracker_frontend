package component

import react.RBuilder
import react.RProps
import react.child
import react.dom.div
import react.dom.p
import react.functionalComponent


const val greeting = "Hello and welcome to simple habit tracker project. " +
        "This project's purpose is to show technical skills required to build a backend server, frontend using react and connect it all together. " +
        "There is no fancy design or any UI design work at all. All this site does is implementing habit tracking application with some features a la to-do list."

val fWelcome =
    functionalComponent<RProps> {
        div("divWithPadding") {
            p {
                +greeting
            }
        }
    }

fun RBuilder.welcomeText(
)=
    child(fWelcome)