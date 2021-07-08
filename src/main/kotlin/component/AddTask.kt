package component

import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.option
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent

interface AddTaskProps: RProps{
    var characterID: Int
    var taskType: String
}

val fAddTask= functionalComponent<AddTaskProps> { props ->
    div("divWithPadding") {
        +"Let's add new ${props.taskType.lowercase()}"
        fieldset {
            +"Name of ${props.taskType.lowercase()}:"
            input {
                attrs.type = InputType.text
                attrs.id = "tName"
                attrs.defaultValue = "Walk with a cat early morning"
            }
        }
        fieldset {
            +"Description:"
            input {
                attrs.type = InputType.text
                attrs.id = "tDescription"
                attrs.defaultValue = "Go outside with chonky boy"
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
        if (props.taskType == "TODO") {
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
            +"Add ${props.taskType.lowercase()}"
        }
    }
}

fun RBuilder.addTask(
    characterID: Int,
    taskType: String
)=child(fAddTask){
    attrs.characterID=characterID
    attrs.taskType=taskType
}