package rssmission.controller

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import rssmission.model.Post
import rssmission.service.PostService
import rssmission.view.RssView
import kotlin.math.min

class RssController(
    val postServiceList: List<PostService>,
    val rssView: RssView,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    var originalPosts = listOf<Post>()

    suspend fun getPosts(checksUpdate: Boolean = false): List<Post> =
        coroutineScope {
            println("[getPosts 1] ${this.coroutineContext}")

            /** 의도적으로 별도의 Context를 사용하면
             * Test 함수와 다른 Context에서 동작하여 delay가 무시되지 않는다
             * (원래 Test함수는 delay를 무시하게 설계됨)
             * */

            withContext(ioDispatcher) {
//                println("[getPosts 2] ${Thread.currentThread().name}")
//                println("[getPosts 2] ${this@coroutineScope.coroutineContext}")
//                delay(10000L)

                val totalList =
                    postServiceList.map { async { it.getPosts() } }
                        .awaitAll()
                        .flatten()

                if (!checksUpdate) originalPosts = totalList
                totalList
            }
        }

    suspend fun getFilteredPosts(keyword: String): List<Post> {
        val filteredByKeyWord =
            getPosts()
                .filter { it.title.contains(keyword) }

        return filteredByKeyWord
            .sortedByDescending { it.date }
            .take(min(10, filteredByKeyWord.size))
    }

    fun printInputMessage() {
        rssView.printInputMessage()
    }

    fun printPosts(postList: List<Post>) {
        rssView.printPostList(postList)
    }

    fun printNewPosts(postList: List<Post>) {
        rssView.printNewPostList(postList)
    }

    fun readInputContent(): String {
        return rssView.readInputContent()
    }

    suspend fun hasOtherPosts(newPosts: List<Post>): List<Post> {
        return newPosts.filter { !originalPosts.contains(it) }
    }
}
