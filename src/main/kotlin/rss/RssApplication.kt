package rss

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rss.controller.RssController
import rss.model.Post
import rss.service.NaverPostService
import rss.service.WoowahanPostService
import rss.view.RssView
import java.time.Duration
import kotlin.math.min

fun main() {
    runBlocking {
        // 1. 컨트롤러, 뷰 생성
        val controller = RssController(WoowahanPostService(), NaverPostService())
        val view = RssView()

        var originPosts: List<Post> = emptyList()

        launch(Dispatchers.IO) {
            while (isActive) {
                // 2. 입력 받을 문구 출력
                view.printInputMessage()
                val keyword = view.getReadLine()

                // 3. RSS 데이터 가져오기
                originPosts = controller.getPosts()

                // 4. 필터링 및 정렬 후 최대 10개만 뽑기
                val filteredPosts =
                    originPosts.filter { it.title.contains(keyword) }
                        .sortedByDescending { it.pubDate }

                // 5. RSS 데이터 출력
                view.printPosts(filteredPosts.take(min(10, filteredPosts.size)))
            }
        }

        launch {
            while (isActive) {
                // 1. 10분마다 확인하면서
                delay(Duration.ofMinutes(10).toMillis())

                // 2. RSS 데이터 가져오기
                val posts = controller.getPosts()

                // 3. origin 과 변경된 것이 있는지 확인
                val newPosts = controller.comparePosts(originPosts, posts)

                // 4. 기존과 다르게 새로 등록된 내용이 있다면 출력
                if (newPosts.isNotEmpty()) {
                    view.printNewPosts(posts)
                }
            }
        }
    }
}
