package component
//
//import data.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import kotlinx.html.style
//import react.*
//import react.dom.*
//
//interface ParticularCharacterProps: RProps {
//    var character : Character
//    var tasks: Array<Task>
//}
//
//interface ParticularCharacterState: RState{
//
//}
//
//val fParticularCharacter= functionalComponent<ParticularCharacterProps> { props ->
//    h3 {
//        +"${props.character.name} the ${props.character.characterClass.lowercase()}"
//    }
//    div {
//        +"Character stats:"
//        ul {
//            li {
//                +"LVL.:${props.character.level}"
//            }
//            li {
//                +"HP:${props.character.healthPoints_}"
//            }
//            li {
//                +"Energy:${props.character.energyPoints}"
//            }
//            li {
//                +"EXP:${props.character.experiencePoints}"
//            }
//            li {
//                +"Coins:${props.character.coins}"
//            }
//        }
//    }
//    div {
//
//        table {
////            attrs.jsStyle="width: 500px;"
//            thead {
//                tr {
//                    th {
//                        +"Habits"
//                    }
//                    th {
//                        +"Dailies"
//                    }
//                    th {
//                        +"To-Do"
//                    }
//                    th {
//                        +"Quests"
//                    }
//                }
//            }
//            tbody {
//                tr {
//                    td {
//                        if (props.tasks.firstOrNull { it.type == "HABIT" } == null) {
//                            +"No habits"
//                        } else {
//                            props.tasks.forEach {
//                                if (it.type == "HABIT") {
//                                    +"${it.name}: ${it.description}."
//                                }
//                            }
//                        }
//                    }
//                    td {
//                        if (props.tasks.firstOrNull { it.type == "DAILY" } == null) {
//                            +"No dailies"
//                        } else {
//                            props.tasks.forEach {
//                                if (it.type == "DAILY") {
//                                    +"${it.name}: ${it.description}."
//                                }
//                            }
//                        }
//                    }
//                    td {
//                        if (props.tasks.firstOrNull { it.type == "TODO" } == null) {
//                            +"No todos"
//                        } else {
//                            props.tasks.forEach {
//                                if (it.type == "TODO") {
//                                    +"${it.name}: ${it.description}."
//                                }
//                            }
//                        }
//                    }
//                    td {
//                        if (props.tasks.firstOrNull { it.type == "QUEST" } == null) {
//                            +"No quests"
//                        } else {
//                            props.tasks.forEach {
//                                if (it.type == "QUEST") {
//                                    +"${it.name}: ${it.description}."
//                                }
//                            }
//                        }
//                    }
//                }
//                tr {
//                    td {
//                        +"Add habit"
//                    }
//                    td {
//                        +"Add daily"
//                    }
//                    td {
//                        +"Add todo"
//                    }
//                    td {
//                        +"Add quest"
//                    }
//                }
//            }
//        }
//    }
//}
//
//fun RBuilder.particularCharacter(
//    character: Character,
//    tasks: Array<Task>
//)=child(fParticularCharacter){
//    attrs.character=character
//    attrs.tasks=tasks
//}