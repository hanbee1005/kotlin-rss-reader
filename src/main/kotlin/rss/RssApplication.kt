package rss

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rss.controller.RssController
import rss.service.NaverPostService
import rss.service.WoowahanPostService
import rss.view.RssView
import kotlin.math.min

fun main() {
    runBlocking {
        // 1. 컨트롤러, 뷰 생성
        val controller = RssController(WoowahanPostService(), NaverPostService())
        val view = RssView()

        launch(Dispatchers.IO) {
            while (isActive) {
                // 2. 입력 받을 문구 출력
                view.printInputMessage()
                val keyword = view.getReadLine()

                // 3. RSS 데이터 가져오기
                val posts = controller.getPosts()

                // 4. 필터링 및 정렬 후 최대 10개만 뽑기
                val filteredPosts =
                    posts.filter { it.title.contains(keyword) }
                        .sortedByDescending { it.pubDate }

                // 5. RSS 데이터 출력
                view.printPosts(filteredPosts.take(min(10, filteredPosts.size)))
            }
        }
    }
}
