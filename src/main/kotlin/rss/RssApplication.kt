package rss

import kotlinx.coroutines.runBlocking
import rss.controller.RssController
import rss.service.NaverPostService
import rss.service.WoowahanPostService
import rss.view.RssView
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        // 1. 컨트롤러, 뷰 생성
        val controller = RssController(WoowahanPostService(), NaverPostService())
        val view = RssView()

        // 2. 입력 받을 문구 출력
        view.printInputMessage()
        val keyword = view.getReadLine()

        val totalTime =
            measureTimeMillis {
                // 3. RSS 데이터 가져오기
                val posts = controller.getPosts(keyword)

                // 4. RSS 데이터 출력
                view.printPosts(posts)
            }

        // 5. 시간 비교
        println("Took $totalTime ms")
    }
}
