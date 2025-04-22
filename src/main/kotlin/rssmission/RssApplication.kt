package rssmission

import kotlinx.coroutines.runBlocking
import rssmission.controller.RssController
import rssmission.service.NaverPostService
import rssmission.service.WoowahanPostService
import rssmission.view.RssView

fun main() {
    // 2단계 미션 기준 코루틴 동시성 로직 적용 전/후 시간 비교를 위함
//    runBlocking {
//        val totalTime =
//            measureTimeMillis {
//                val controller = RssController(WoowahanPostService(), NaverPostService(), RssView())
//
//                controller.printInputMessage()
//                val keyword = controller.readInputContent()
//                val postList = controller.getPosts(keyword)
//                controller.printPosts(postList)
//            }
//
//        println("\n전체 작업 소요 시간 : $totalTime ms")
//    }

    runBlocking {
        val controller = RssController(WoowahanPostService(), NaverPostService(), RssView())

        while (true) {
            controller.printInputMessage()
            val keyword = controller.readInputContent()
            val postList = controller.getFilteredPosts(keyword)
            controller.printPosts(postList)
        }
    }
}
