package component

import data.Item
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent

interface ShopProps : RProps {
    var shopStatus: Array<Item>
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
                    }
                }
            }
        }
    }
}

fun RBuilder.shop(
    shopStatus: Array<Item>
)=child(fShop){
    attrs.shopStatus=shopStatus
}