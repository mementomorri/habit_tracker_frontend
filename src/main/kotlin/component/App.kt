package component

import data.*
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
    var adventurers : Array<Adventurer>
    var objectives: Array<Objective>
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
            val fetchedAdventurers= JSON.parse<Array<Adventurer>>(fetchData(rootURL+"adventurers"))
            val fetchedObjectives= JSON.parse<Array<Objective>>(fetchData(rootURL+"objective"))
            val shopData= JSON.parse<Array<Item>>(fetchData(rootURL+"shop"))
            val fetchedBuffs= arrayOf<Array<Buff>>()
            val fetchedInventory= arrayOf<Array<Item>>()
            fetchedAdventurers.forEach {
                fetchedBuffs[it.id-1] = JSON.parse(fetchData(rootURL+"adventurers/${it.id}/buffs"))
                fetchedInventory[it.id-1] = JSON.parse(fetchData(rootURL+"adventurers/${it.id}/inventory"))
            }
            setState {
                adventurers = fetchedAdventurers
                objectives=fetchedObjectives
                buffs=fetchedBuffs
                inventories=fetchedInventory
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
                            navLink<RProps>("/adventurers") { +"Adventurers" }
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
            route("/adventurers",
                exact=true,
                render = {adventurers(state.adventurers)}
            )
            route("/about",
                exact=true,
                render = { about() }
            )
            route("/adventurers/:number",
                exact=true,
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                        val num = route_props.num()
                        val chosenAdventurer = state.adventurers.firstOrNull { it.id == num }
                        if ( chosenAdventurer != null ) {
                            particularAdventurer(chosenAdventurer,getObjectivesByAdv(num), state.buffs[num-1], state.inventories[num-1], ::completeObjective)
                        }else{
                            p { +"No such adventurer with id ${route_props.match.params.number.toInt()}" }
                        }
                }
            )
            route("/shop",
                exact=true,
                render={ shop(state.shopStatus,::buyItem) }
            )
            route("/adventurer/:number/addhabit",
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.num()
                    val chosenAdventurer = state.adventurers.firstOrNull { it.id == num }
                    if ( chosenAdventurer != null ) {
                        addObjective(chosenAdventurer.id,"HABIT",state.objectives.size+1, ::addObjective)
                    }else{
                        p { +"Can't add habit" }
                    }
                }
            )
            route("/adventurer/:number/adddaily",
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.num()
                    val chosenAdventurer = state.adventurers.firstOrNull { it.id == num }
                    if ( chosenAdventurer != null ) {
                        addObjective(chosenAdventurer.id,"DAILY",state.objectives.size+1, ::addObjective)
                    }else{
                        p { +"Can't add daily" }
                    }
                }
            )
            route("/adventurer/:number/addtodo",
                render={ route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.num()
                    val chosenAdventurer = state.adventurers.firstOrNull { it.id == num }
                    if ( chosenAdventurer != null ) {
                        addObjective(chosenAdventurer.id,"TODO",state.objectives.size+1,::addObjective)
                    }else{
                        p { +"Can't add todo" }
                    }
                }
            )
            route("/objective/:number",
                    render = {route_props: RouteResultProps<RouteNumberResult> ->
                        val num = route_props.num()
                        val chosenObjective=state.objectives.firstOrNull { it.id ==num }
                        if (chosenObjective != null) {
                            editObjective(chosenObjective, ::addObjective, ::deleteObjective)
                        }else{
                            p{+"Can't find such a task"}
                        }
                    }
                )
        }
    }

    fun buyItem(item: Item, advId: Int){
        val advBuyer= state.adventurers.firstOrNull { it.id==advId }
        if (advBuyer != null) {
            val result=state.inventories.toMutableList()
            result[advId][-1]=item
            setState {
                inventories=result.toTypedArray()
            }
            scope.launch {
                postData(item, rootURL + "adventurers/$advId/inventory")
            }
        }
    }

    private fun getObjectivesByAdv(adv: Int): Array<Objective>{
        val result = state.objectives.filter { it.adventurerId == adv }
        return result.toTypedArray()
    }

    fun addObjective(newObjective: Objective){
        val result= state.objectives.toMutableList()
        result.add(newObjective)
        setState{
            objectives=result.toTypedArray()
        }
        scope.launch {
            postData(newObjective, "objective")
        }
        refreshState()
    }

    fun deleteObjective(objectiveID: Int){
        val result= state.objectives.toMutableList()
        val objToDelete=state.objectives.firstOrNull { it.id==objectiveID }
        if (objToDelete != null){
            result.remove(objToDelete)
            setState {
                objectives=result.toTypedArray()
            }
            scope.launch {
                Request.delete {
                    url(rootURL+"objective/$objectiveID")
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            }
        }
    }

    fun completeObjective(objectiveID: Int){
        val objToComplete= state.objectives.firstOrNull { it.id == objectiveID }
        if (objToComplete != null){
            scope.launch {
                Request.get {
                    url(rootURL+"objective/${objectiveID}/${objToComplete.adventurerId}/complete")
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            }
            refreshState()
        }
    }

    suspend fun <T>postData(data: T, urlToPost:String): HttpStatusCode {
        return Request.post {
            url(rootURL+urlToPost)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            body = data!!
        }
    }

    fun refreshState(){
        scope.launch {
            val fetchedAdventurers= JSON.parse<Array<Adventurer>>(fetchData(rootURL+"adventurers"))
            val fetchedObjectives= JSON.parse<Array<Objective>>(fetchData(rootURL+"objective"))
            setState {
                adventurers = fetchedAdventurers
                objectives = fetchedObjectives
            }
        }
    }

    fun refreshStateOfAdventurer(adventurerID: Int){
        scope.launch {
            val fetchedAdventurer= JSON.parse<Adventurer>(fetchData(rootURL+"adventurers/$adventurerID"))
            val copyOfAdventurers= state.adventurers.toMutableList()
            copyOfAdventurers.removeAll { it.id==adventurerID }
            copyOfAdventurers.add(fetchedAdventurer)
            val fetchedObjectives= JSON.parse<Array<Objective>>(fetchData(rootURL+"objective/byadventurer/$adventurerID"))
            val copyOfObjectives= state.objectives.toMutableList()
            copyOfObjectives.removeAll { it.adventurerId == adventurerID }
            fetchedObjectives.forEach {
                copyOfObjectives.add(it)
            }
            setState {
                adventurers = copyOfAdventurers.toTypedArray()
                objectives = copyOfObjectives.toTypedArray()
            }
        }
    }
}




fun RBuilder.app(

) = child(App::class) {

}