package component

import react.RBuilder
import react.RProps
import react.child
import react.dom.p
import react.functionalComponent

const val aboutme = "I was solely created this app to just show what I can do as a junior developer, please don't judge too rough on me. "

val fAbout=
    functionalComponent<RProps> {
        p {
            +aboutme
        }
    }

fun RBuilder.about()=
    child(fAbout)