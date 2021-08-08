package component

import data.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import react.router.dom.navLink
import services.Request
import services.scope

interface ParticularAdventurerProps: RProps {
    var adventurer : Adventurer
    var objectives: Array<Objective>
    var buffs: Array<Buff>
    var inventory: Array<Item>
    var completeObjective: (Int) -> Unit
}

val fParticularAdventurer= functionalComponent<ParticularAdventurerProps> { props ->
    h3 {
        +"${props.adventurer.name} the ${props.adventurer.adventurerClass.lowercase()}"
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
        +"Adventurer stats:"
        ul {
            li {
                +"LVL.:${props.adventurer.level}"
            }
            li {
                +"HP:${props.adventurer.healthPoints_}/${props.adventurer.maximumHP_} (${
                    (props.adventurer.healthPoints_?.div(
                        props.adventurer.maximumHP_!!
                    ) ?: 0) * 100
                }%)"
            }
        }
    }
    div {
        ul {
            li {
                +"Energy:${props.adventurer.energyPoints}/100"
            }
            li {
                +"EXP:${props.adventurer.experiencePoints}"
            }
            li {
                +"Coins:${props.adventurer.coins}"
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
                val typesOfObjectives = listOf("HABIT", "DAILY", "TODO", "QUEST")
                var numberOfRows = 0
                typesOfObjectives.forEach { type ->
                    val listOfObjectives = props.objectives.filter { it.type == type }
                    if (listOfObjectives.size > numberOfRows) numberOfRows = listOfObjectives.size
                }
                val copyOfObjectives = props.objectives.toMutableList()
                while (numberOfRows != 0) {
                    tr {
                        td {
                            val currentHabit = copyOfObjectives.firstOrNull { it.type == "HABIT" }
                            if (currentHabit == null) {
                                +""
                            } else {
                                navLink<RProps>("/objective/${currentHabit.id}"){+"${currentHabit.name}: ${currentHabit.description}"}
                                br {  }
                                button {
                                    +"+"
                                    attrs.onClickFunction={
                                        props.completeObjective(currentHabit.id)
                                    }
                                }
                                copyOfObjectives.remove(currentHabit)
                            }
                        }
                        td {
                            val currentDaily = copyOfObjectives.firstOrNull { it.type == "DAILY" }
                            if (currentDaily == null) {
                                +""
                            } else {
                                navLink<RProps>("/objective/${currentDaily.id}"){+"${currentDaily.name}: ${currentDaily.description}"}
                                br {  }
                                button {
                                    +"+"
                                    attrs.onClickFunction={
                                        props.completeObjective(currentDaily.id)
                                    }
                                }
                                copyOfObjectives.remove(currentDaily)
                            }
                        }
                        td {
                            val currentTodo = copyOfObjectives.firstOrNull { it.type == "TODO" }
                            if (currentTodo == null) {
                                +""
                            } else {
                                navLink<RProps>("/objective/${currentTodo.id}"){+"${currentTodo.name}: ${currentTodo.description}"}
                                br {  }
                                button {
                                    +"+"
                                    attrs.onClickFunction={
                                        props.completeObjective(currentTodo.id)
                                    }
                                }
                                copyOfObjectives.remove(currentTodo)
                            }
                        }
                        td {
                            val currentQuest = copyOfObjectives.firstOrNull { it.type == "QUEST" }
                            if (currentQuest == null) {
                                +""
                            } else {
                                navLink<RProps>("/objective/${currentQuest.id}"){+"${currentQuest.name}: ${currentQuest.description}"}
                                br {  }
                                button {
                                    +"+"
                                    attrs.onClickFunction={
                                        props.completeObjective(currentQuest.id)
                                    }
                                }
                                copyOfObjectives.remove(currentQuest)
                            }
                        }
                    }
                    numberOfRows -= 1
                }
                tr {
                    th {
                        navLink<RProps>("/adventurer/${props.adventurer.id}/addhabit"){+"Add habit"}
                    }
                    th {
                        navLink<RProps>("/adventurer/${props.adventurer.id}/adddaily"){+"Add daily"}
                    }
                    th {
                        navLink<RProps>("/adventurer/${props.adventurer.id}/addtodo"){+"Add todo"}
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

fun RBuilder.particularAdventurer(
    adventurer: Adventurer,
    objectives: Array<Objective>,
    buffs: Array<Buff>,
    inventory: Array<Item>,
    completeObjective: (Int) -> Unit
)=child(fParticularAdventurer){
    attrs.adventurer=adventurer
    attrs.objectives=objectives
    attrs.buffs=buffs
    attrs.inventory=inventory
    attrs.completeObjective= completeObjective
}