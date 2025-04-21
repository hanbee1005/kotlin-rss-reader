package rssmission

import rssmission.controller.RssController
import rssmission.service.NaverPostService
import rssmission.service.WoowahanPostService
import rssmission.view.RssView

fun main() {
    val controller = RssController(WoowahanPostService(), NaverPostService(), RssView())

    controller.printInputMessage()
    val keyword = controller.readInputContent()
    val postList = controller.getPosts(keyword)
    controller.printPosts(postList)
}
