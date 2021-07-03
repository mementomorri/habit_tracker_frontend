package component

import data.Character
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

}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {


    private suspend fun fetchCharacters(): String {
        return try {
            Request.get {
                url(rootURL+"character")
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            console.error("Error occurred when trying to get characters list")
            ""
        }
    }

    private suspend fun fetchTasks(characterID:Int): String {
        return try {
            Request.get {
                url(rootURL+"character/${characterID}/tasks")
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
            val result = fetchCharacters()
            val parsedData = JSON.parse<Array<Character>>(result)
            val twoDimensionalArray= arrayOf<Array<Task>>()
            parsedData.forEach {
                twoDimensionalArray[it.id-1] = JSON.parse<Array<Task>>(fetchTasks(it.id))
            }
            setState {
                characters = parsedData
            }
            setState{
                tasks=twoDimensionalArray
            }
        }
    }

    override fun RBuilder.render() {
        header {
            h1 {
                +"Simple habit tracker"
            }

            div {
                nav {
                    ul {
                        li {
                            navLink<RProps>("/") { +"Welcome" }
                        }
                        li {
                            navLink<RProps>("/characters"){+"Characters"}
                        }
                        li {
                            navLink<RProps>("/about"){+"About developer"}
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
                        val num = route_props.match.params.number.toIntOrNull() ?: -1
                        val chosenCharacter = state.characters.firstOrNull {
                            it.id == num
                        }
                        if ( chosenCharacter != null ) {
//                            scope.launch {
//                                val result= fetchTasks(num)
//                                val parsedData=JSON.parse<Array<Task>>(result)
//                                setState{
//                                    tasks=parsedData
//                                }
//                            }
                            particularCharacter(chosenCharacter,state.tasks[num-1])
                        }else{
                            p { +"No such a character with id ${route_props.match.params.number.toInt()}" }
                        }
                }
            )
        }
    }
}




fun RBuilder.app(

) = child(App::class) {

}