import component.app
import kotlinx.browser.document
import react.dom.render
import react.router.dom.browserRouter


fun main() {
    render(document.getElementById("root")!!) {
        browserRouter {
            app()
        }
    }
}
