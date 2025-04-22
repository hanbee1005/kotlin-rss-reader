package rssmission.controller

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import rssmission.model.Post
import rssmission.service.NaverPostService
import rssmission.service.WoowahanPostService
import rssmission.view.RssView
import kotlin.math.min

class RssController(
    val woowahanPostService: WoowahanPostService,
    val naverPostService: NaverPostService,
    val rssView: RssView,
) {
    var originalPosts = listOf<Post>()

    suspend fun getPosts(checksUpdate: Boolean = false): List<Post> =
        coroutineScope {
            val woowahanPostList = async { woowahanPostService.getPosts() }
            val naverPostList = async { naverPostService.getPosts() }
            val totalList = woowahanPostList.await() + naverPostList.await()

            if (!checksUpdate) originalPosts = totalList
            totalList
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
