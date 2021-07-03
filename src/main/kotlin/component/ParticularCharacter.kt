package component

import data.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.html.style
import react.*
import react.dom.*

interface ParticularCharacterProps: RProps {
    var character : Character
    var tasks: Array<Task>
}

interface ParticularCharacterState: RState{

}

val fParticularCharacter= functionalComponent<ParticularCharacterProps> { props ->
    h3 {
        +"${props.character.name} the ${props.character.characterClass.lowercase()}"
    }
    div {
        +"Character stats:"
        ul {
            li {
                +"LVL.:${props.character.level}"
            }
            li {
                +"HP:${props.character.healthPoints_}"
            }
            li {
                +"Energy:${props.character.energyPoints}"
            }
            li {
                +"EXP:${props.character.experiencePoints}"
            }
            li {
                +"Coins:${props.character.coins}"
            }
        }
    }
    div {

        table {
//            attrs.jsStyle="width: 500px;"
            thead {
                tr {
                    th {
                        +"Habits"
                    }
                    th {
                        +"Dailies"
                    }
                    th {
                        +"To-Do"
                    }
                    th {
                        +"Quests"
                    }
                }
            }
            tbody {
                val typesOfTasks= listOf("HABIT","DAILY","TODO","QUEST")
                var numberOfRows = 0
                typesOfTasks.forEach { type ->
                    val listOfTasks=props.tasks.filter { it.type == type }
                    if (listOfTasks.size > numberOfRows) numberOfRows=listOfTasks.size
                }
                val copyOfTasks = props.tasks.toMutableList()
                while (numberOfRows != 0) {
                    tr {
                        td {
                            val currentHabit = copyOfTasks.firstOrNull { it.type == "HABIT" }
                            if (currentHabit == null) {
                                +""
                            } else {
                                +"${currentHabit.name}: ${currentHabit.description}"
                                copyOfTasks.remove(currentHabit)
                            }
                        }
                        td {
                            val currentDaily = copyOfTasks.firstOrNull { it.type == "DAILY" }
                            if (currentDaily == null) {
                                +""
                            } else {
                                +"${currentDaily.name}: ${currentDaily.description}"
                                copyOfTasks.remove(currentDaily)
                            }
                        }
                        td {
                            val currentTodo = copyOfTasks.firstOrNull { it.type == "TODO" }
                            if (currentTodo == null) {
                                +""
                            } else {
                                +"${currentTodo.name}: ${currentTodo.description}"
                                copyOfTasks.remove(currentTodo)
                            }
                        }
                        td {
                            val currentQuest = copyOfTasks.firstOrNull { it.type == "QUEST" }
                            if (currentQuest == null) {
                                +""
                            } else {
                                +"${currentQuest.name}: ${currentQuest.description}"
                                copyOfTasks.remove(currentQuest)
                            }
                        }
                    }
                    numberOfRows -= 1
                }
//                tfoot {
                    tr {
                        th {
                            +"Add habit"
                        }
                        th {
                            +"Add daily"
                        }
                        th {
                            +"Add todo"
                        }
                        th {
                            +"Add quest"
                        }
                    }
//                }
            }
        }
    }
}

fun RBuilder.particularCharacter(
    character: Character,
    tasks: Array<Task>
)=child(fParticularCharacter){
    attrs.character=character
    attrs.tasks=tasks
}