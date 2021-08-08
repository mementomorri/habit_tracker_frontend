package component

import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent
import react.router.dom.navLink

val fAbout=
    functionalComponent<RProps> {
        div("divWithPadding") {
            span {
                +"This app was solely designed to show what I can do as a junior developer, here is a link to my "
                a("https://mementomorri.github.io") { +"portfolio" }
                +"."
            }
        }
    }

fun RBuilder.about()=
    child(fAbout)