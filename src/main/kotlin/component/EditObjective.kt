package component

import data.Objective
import kotlinx.browser.document
import kotlinx.browser.window
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

interface EditObjectiveProps: RProps{
    var objective: Objective
    var addObj: (Objective) ->Unit
    var deleteObjective: (Int) ->Unit
}

val fEditObjective= functionalComponent<EditObjectiveProps> { props ->
    div("divWithPadding") {
        +"Edit ${props.objective.type.lowercase()}"
        fieldset {
            +"Name of ${props.objective.type.lowercase()}:"
            input {
                attrs.type = InputType.text
                attrs.id = "tName"
                attrs.defaultValue = props.objective.name
            }
        }
        fieldset {
            +"Description:"
            input {
                attrs.type = InputType.text
                attrs.id = "tDescription"
                attrs.defaultValue = props.objective.description
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
        if (props.objective.type == "TODO") {
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
            +"Edit ${props.objective.type.lowercase()}"
            attrs.onClickFunction={
                props.deleteObjective(props.objective.id)
                val objectiveName= document.getElementById("tName")
                        as HTMLInputElement
                val objectiveDescription= document.getElementById("tDescription")
                        as HTMLInputElement
                val objectiveDifficulty= document.getElementById("tDiff")
                        as HTMLSelectElement
                props.addObj(
                    Objective(
                        props.objective.id,
                        objectiveName.value,
                        objectiveDescription.value,
                        props.objective.type.uppercase(),
                        props.objective.adventurerId,
                        objectiveDifficulty.value,
                        null,
                        null,
                        0
                    )
                )
                window.history.back()
            }
        }
        button {
            +"Delete ${props.objective.type.lowercase()}"
            attrs.onClickFunction={
                props.deleteObjective(props.objective.id)
                window.history.back()
            }
        }
    }
}

fun RBuilder.editObjective(
    objective: Objective,
    addObj: (Objective) -> Unit,
    deleteObjective: (Int) -> Unit
)=child(fEditObjective){
    attrs.objective = objective
    attrs.addObj = addObj
    attrs.deleteObjective = deleteObjective
}