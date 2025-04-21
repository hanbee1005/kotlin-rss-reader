package rssmission

import rssmission.controller.RssController
import rssmission.service.WoowahanPostService
import rssmission.view.RssView

fun main() {
    val controller = RssController(WoowahanPostService(), RssView())

    val postList = controller.getPosts()
    controller.printPosts(postList)
}
