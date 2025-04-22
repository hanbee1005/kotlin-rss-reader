package rss

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rss.controller.RssController
import rss.service.NaverPostService
import rss.service.WoowahanPostService

class RssTest {
    @ExperimentalStdlibApi
    @Test
    @DisplayName("지정 시간 지난 후 새로운 post 를 가지고 왔는지 확인하는 테스트")
    fun rssTimeTest() =
        runTest {
            // testScheduler : 테스트 시 시간 조작을 위해 testScheduler 필요함
            val dispatcher = StandardTestDispatcher(testScheduler)

            val services = listOf(WoowahanPostService(), NaverPostService())
            val controller = RssController(services, dispatcher)

            originPosts = listOf()

            val job =
                async {
                    println("[test 1] ${Thread.currentThread().name}")

                    println("[test 1] ${this.coroutineContext}")
                    println("[test 1] ${this@runTest.coroutineContext}")

                    println("[test 1] ${(this.coroutineContext)[CoroutineDispatcher]}")
                    println("[test 1] ${(this@runTest.coroutineContext)[CoroutineDispatcher]}")

                    controller.getPosts()
                }

            // advanceTimeBy(10000)

            val newPosts = job.await()

            println("size!! : ${newPosts.size}")
            newPosts.size shouldBe controller.comparePosts(originPosts, newPosts).size
        }
}
