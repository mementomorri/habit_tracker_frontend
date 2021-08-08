package component

import data.Item
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent

interface ShopProps : RProps {
    var shopStatus: Array<Item>
    var buyItmen: (Item, Int) -> Unit
}

val fShop= functionalComponent<ShopProps> { props ->
    div("divWithPadding") {
        +"Magic shop for people who wants to become better"
        table {
            thead {
                tr {
                    th {
                        +"Item name"
                    }
                    th {
                        +"Description"
                    }
                    th {
                        +"Price"
                    }
                    th {
                        +"Items left"
                    }
                    th {
                        +""
                    }
                }
            }
            tbody {
                props.shopStatus.forEach { item ->
                    tr {
                        td {
                            +item.name
                        }
                        td {
                            +item.description
                        }
                        td {
                            +item.price.toString()
                        }
                        td {
                            +item.quantity.toString()
                        }
                        td {
                            button {
                                +"Buy"
                                attrs.onClickFunction={
                                    props.buyItmen(item, 3)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.shop(
    shopStatus: Array<Item>,
    buyItmen: (Item, Int) -> Unit
)=child(fShop){
    attrs.shopStatus=shopStatus
    attrs.buyItmen=buyItmen
}