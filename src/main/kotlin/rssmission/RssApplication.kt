package rssmission

import kotlinx.coroutines.runBlocking
import rssmission.controller.RssController
import rssmission.service.NaverPostService
import rssmission.service.WoowahanPostService
import rssmission.view.RssView
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val totalTime =
            measureTimeMillis {
                val controller = RssController(WoowahanPostService(), NaverPostService(), RssView())

                controller.printInputMessage()
                val keyword = controller.readInputContent()
                val postList = controller.getPosts(keyword)
                controller.printPosts(postList)
            }

        println("\n전체 작업 소요 시간 : $totalTime ms")
    }
}
