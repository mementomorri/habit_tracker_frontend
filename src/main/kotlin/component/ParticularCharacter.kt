package component

import data.Buff
import data.Character
import data.Item
import data.Task
import react.*
import react.dom.*
import react.router.dom.navLink

interface ParticularCharacterProps: RProps {
    var character : Character
    var tasks: Array<Task>
    var buffs: Array<Buff>
    var inventory: Array<Item>
}

val fParticularCharacter= functionalComponent<ParticularCharacterProps> { props ->
    h3 {
        +"${props.character.name} the ${props.character.characterClass.lowercase()}"
    }
    div("divWithSmallPadding") {
        val buffStatus = ""
        props.buffs.forEach { buffStatus.plus(it.name + " ") }
        if (buffStatus.isBlank()) {
            +"Active buffs: Nothing affects you today."
        } else {
            +"Active buffs: $buffStatus"
        }
    }
    div("divWithSmallPadding") {
        +"Character stats:"
        ul {
            li {
                +"LVL.:${props.character.level}"
            }
            li {
                +"HP:${props.character.healthPoints_}/${props.character.maximumHP_} (${
                    (props.character.healthPoints_?.div(
                        props.character.maximumHP_!!
                    ) ?: 0) * 100
                }%)"
            }
        }
    }
    div {
        ul {
            li {
                +"Energy:${props.character.energyPoints}/100"
            }
            li {
                +"EXP:${props.character.experiencePoints}"
            }
            li {
                +"Coins:${props.character.coins}"
            }
        }
    }
    div("divWithPadding") {
        table {
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
                val typesOfTasks = listOf("HABIT", "DAILY", "TODO", "QUEST")
                var numberOfRows = 0
                typesOfTasks.forEach { type ->
                    val listOfTasks = props.tasks.filter { it.type == type }
                    if (listOfTasks.size > numberOfRows) numberOfRows = listOfTasks.size
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
                tr {
                    th {
                        navLink<RProps>("/character/${props.character.id}/addhabit"){+"Add habit"}
                    }
                    th {
                        navLink<RProps>("/character/${props.character.id}/adddaily"){+"Add daily"}
                    }
                    th {
                        navLink<RProps>("/character/addtodo/${props.character.id}"){+"Add todo"}
                    }
                    th {
                        navLink<RProps>("/shop"){+"Shop for quest scrolls"}
                    }
                }
            }
        }
    }
    div("divWithSmallPadding") {
        val inventoryStatus = ""
        props.inventory.forEach { inventoryStatus.plus(it.name + " ") }
        if (inventoryStatus.isBlank()) {
            +"Inventory: Seems like your backpack is empty."
        } else {
            +"Inventory: $inventoryStatus"
        }
    }
}

fun RBuilder.particularCharacter(
    character: Character,
    tasks: Array<Task>,
    buffs: Array<Buff>,
    inventory: Array<Item>
)=child(fParticularCharacter){
    attrs.character=character
    attrs.tasks=tasks
    attrs.buffs=buffs
    attrs.inventory=inventory
}