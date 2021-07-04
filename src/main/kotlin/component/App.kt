package component

import data.Buff
import data.Character
import data.Item
import data.Task
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import react.*
import react.dom.*
import react.router.dom.RouteResultProps
import react.router.dom.navLink
import react.router.dom.route
import react.router.dom.switch
import services.Request
import services.scope

const val rootURL="https://kotlin-habit-tracker.herokuapp.com/"

interface AppProps : RProps {

}

interface AppState : RState {
    var characters : Array<Character>
    var tasks: Array<Array<Task>>
    var buffs: Array<Array<Buff>>
    var inventories: Array<Array<Item>>
    var shopStatus: Array<Item>
}

interface RouteNumberResult : RProps {
    var number: String
}

fun RouteResultProps<RouteNumberResult>.num()=
    this.match.params.number.toIntOrNull() ?: -1

class App : RComponent<AppProps, AppState>() {


    private suspend fun fetchData(url:String): String {
        return try {
            Request.get {
                url(url)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            console.error("Error occurred when trying to get characters list")
            ""
        }
    }

    override fun componentDidMount() {
        scope.launch {
            val result = fetchData(rootURL+"character")
            val parsedData = JSON.parse<Array<Character>>(result)
            val twoDimensionalArray= arrayOf<Array<Task>>()
            val twoDimensionalArray2= arrayOf<Array<Buff>>()
            val twoDimensionalArray3= arrayOf<Array<Item>>()
            parsedData.forEach {
                twoDimensionalArray[it.id-1] = JSON.parse<Array<Task>>(fetchData(rootURL+"character/${it.id}/tasks"))
                twoDimensionalArray2[it.id-1] = JSON.parse<Array<Buff>>(fetchData(rootURL+"character/${it.id}/buffs"))
                twoDimensionalArray3[it.id-1] = JSON.parse<Array<Item>>(fetchData(rootURL+"character/${it.id}/inventory"))
            }
            val shopData= JSON.parse<Array<Item>>(fetchData(rootURL+"shop"))
            setState {
                characters = parsedData
            }
            setState{
                tasks=twoDimensionalArray
            }
            setState{
                buffs=twoDimensionalArray2
            }
            setState{
                inventories=twoDimensionalArray3
            }
            setState {
                shopStatus = shopData
            }
        }
    }

    override fun RBuilder.render() {
        header {
            div {
                div("headerName") {
                    +"Habit tracker"
                }
                div("headerList") {
                    ul {
                        li {
                            navLink<RProps>("/") { +"Welcome" }
                        }
                        li {
                            navLink<RProps>("/characters") { +"Characters" }
                        }
                        li {
                            navLink<RProps>("/about") { +"About dev" }
                        }
                    }
                }
            }
        }
        switch {
            route("/",
                exact = true,
                render = { welcomeText() }
            )
            route("/characters",
                exact=true,
                render = {characters(state.characters)}
            )
            route("/about",
                exact=true,
                render = { about() }
            )
            route("/character/:number",
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                        val num = route_props.num()
                        val chosenCharacter = state.characters.firstOrNull { it.id == num }
                        if ( chosenCharacter != null ) {
                            particularCharacter(chosenCharacter,state.tasks[num-1], state.buffs[num-1], state.inventories[num-1])
                        }else{
                            p { +"No such a character with id ${route_props.match.params.number.toInt()}" }
                        }
                }
            )
            route("/shop",
                exact=true,
                render={ shop(state.shopStatus) }
            )
            route("/character/:number/addhabit",
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.num()
                    val chosenCharacter = state.characters.firstOrNull { it.id == num }
                    if ( chosenCharacter != null ) {
                        addTask(chosenCharacter.id,"HABIT")
                    }else{
                        p { +"Can't add habit" }
                    }
                }
            )
            route("/character/:number/adddaily",
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.num()
                    val chosenCharacter = state.characters.firstOrNull { it.id == num }
                    if ( chosenCharacter != null ) {
                        addTask(chosenCharacter.id,"DAILY")
                    }else{
                        p { +"Can't add daily" }
                    }
                }
            )
            route("/character/addtodo/:number",
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.num()
                    val chosenCharacter = state.characters.firstOrNull { it.id == num }
                    if ( chosenCharacter != null ) {
                        addTask(chosenCharacter.id,"TODO")
                    }else{
                        p { +"Can't add todo" }
                    }
                }
            )
        }
    }
}




fun RBuilder.app(

) = child(App::class) {

}