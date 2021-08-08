package component

import data.Objective
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent
import react.router.dom.navLink
import react.router.dom.redirect
import services.Request
import services.scope

interface AddObjectiveProps: RProps{
    var adventurerID: Int
    var objectiveType: String
    var lastObjectiveID: Int
    var onClick:(Objective)->Unit
}

val fAddObjective= functionalComponent<AddObjectiveProps> { props ->
    div("divWithPadding") {
        +"Let's add new ${props.objectiveType.lowercase()}"
        fieldset {
            +"Name of ${props.objectiveType.lowercase()}:"
            input {
                attrs.type = InputType.text
                attrs.id = "tName"
                attrs.defaultValue = "Have a little walk at the morning"
            }
        }
        fieldset {
            +"Description:"
            input {
                attrs.type = InputType.text
                attrs.id = "tDescription"
                attrs.defaultValue = "Go outside before doing anything in a day"
            }
        }
        fieldset {
            +"Difficulty:"
            select {
                attrs.id = "tDiff"
                option { +"VeryEasy" }
                option { +"Easy" }
                option { +"Medium" }
                option { +"Hard" }
                option { +"VeryHard" }
            }
        }
        if (props.objectiveType == "TODO") {
            fieldset {
                +"Deadline:"
                input {
                    attrs.type = InputType.date
                    attrs.id = "tDeadline"
                }
            }
        }
        button {
            attrs.type = ButtonType.submit
            +"Add ${props.objectiveType.lowercase()}"
            attrs.onClickFunction={
                val objectiveName= document.getElementById("tName")
                        as HTMLInputElement
                val objectiveDescription= document.getElementById("tDescription")
                        as HTMLInputElement
                val objectiveDifficulty= document.getElementById("tDiff")
                        as HTMLSelectElement
                    props.onClick(
                        Objective(
                            props.lastObjectiveID,
                            objectiveName.value,
                            objectiveDescription.value,
                            props.objectiveType.uppercase(),
                            props.adventurerID,
                            objectiveDifficulty.value,
                            null,
                            null,
                            0
                        )
                    )
                window.history.back()
            }
        }
    }
}

fun RBuilder.addObjective(
    characterID: Int,
    taskType: String,
    lastObjectiveID: Int,
    onClick: (Objective) ->Unit
)=child(fAddObjective){
    attrs.adventurerID=characterID
    attrs.objectiveType=taskType
    attrs.lastObjectiveID=lastObjectiveID
    attrs.onClick=onClick
}