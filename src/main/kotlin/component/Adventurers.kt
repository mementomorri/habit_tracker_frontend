package component

import data.Adventurer
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent
import react.router.dom.navLink

interface AdventurersProps: RProps {
    var adventurers : Array<Adventurer>
}

val fAdventurers=
    functionalComponent<AdventurersProps> { props ->
        div{
            div("divWithPadding") {
                +"Table of adventurers:"
            }
            table {
                thead {
                    tr {
                        th {
                            +"Name"
                        }
                        th {
                            +"Class"
                        }
                        th {
                            +"Level"
                        }
                    }
                }
                tbody {
                    props.adventurers.forEach { adventurer ->
                        tr {
                            td("commonLink") {
                                navLink<RProps>("/adventurers/${adventurer.id}"){+adventurer.name}
                            }
                            td {
                                +adventurer.adventurerClass.lowercase()
                            }
                            td {
                                +adventurer.level.toString()
                            }
                        }
                    }
                }
            }
        }
    }


fun RBuilder.adventurers(
    adventurers:Array<Adventurer>
)=child(fAdventurers){
    attrs.adventurers = adventurers
}