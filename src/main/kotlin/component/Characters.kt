package component

import data.Character
import kotlinx.browser.document
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent
import react.router.dom.navLink

interface CharactersProps: RProps {
    var characters : Array<Character>
}

val fCharacters=
    functionalComponent<CharactersProps> {props ->

        div {
            +"table of characters:"
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
                    props.characters.forEach { character ->
                        tr {
                            td {
                                navLink<RProps>("/character/${character.id}"){+character.name}
                            }
                            td {
                                +character.characterClass.lowercase()
                            }
                            td {
                                +character.level.toString()
                            }
                        }
                    }
                }
            }
        }
    }


fun RBuilder.characters(
    characters:Array<Character>
)=child(fCharacters){
    attrs.characters = characters
}