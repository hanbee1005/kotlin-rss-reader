import io.kotest.matchers.shouldBe
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import rssmission.controller.RssController
import rssmission.model.Post
import rssmission.service.NaverPostService
import rssmission.service.WoowahanPostService
import rssmission.view.RssView

class RssTest {
    @Test
    @DisplayName("지정 시간 지난 후 메서드 호출 수행하는지 테스트")
    fun rssTimeTest() =
        runTest {
            val controller = RssController(WoowahanPostService(), NaverPostService(), RssView())

            controller.originalPosts = listOf<Post>(Post())

            val job =
                async {
                    delay(10000)
                    controller.getPosts(true)
                }

            advanceTimeBy(10000)

            val newPosts = job.await()

            newPosts.size shouldBe controller.hasOtherPosts(newPosts).size
        }
}
