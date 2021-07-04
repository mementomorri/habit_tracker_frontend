package component

import kotlinx.html.InputType
import react.RBuilder
import react.RProps
import react.child
import react.dom.div
import react.dom.form
import react.dom.input
import react.dom.label
import react.functionalComponent

interface AddTaskProps: RProps{
    var characterID: Int
    var taskType: String
}

val fAddTask= functionalComponent<AddTaskProps> { props ->
    div {
        +"Let's add new ${props.taskType.lowercase()}"
        form {
            label{
                +"Name:"
                attrs.htmlFor="tName"
            }
            input(InputType.text,name = "tName"){+"Walk with a cat early morning"}
            label {
                +"Description:"
                attrs.htmlFor="tDescription"
            }
            input(InputType.text, name = "tDescription"){+"Go outside with chonky boy"}
            label {
                +"Difficulty:"
                attrs.htmlFor="tDiff"
            }
            input(InputType.radio, name="tDiff"){
                +"VeryEasy"
                +"Easy"
                +"Medium"
                +"Hard"
                +"VeryHard"
            }
            if (props.taskType =="TODO"){
                label {
                    +"Deadline:"
                    attrs.htmlFor="tDeadline"
                }
                input(InputType.text, name="tDeadline"){+"12/21/277"}
            }
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