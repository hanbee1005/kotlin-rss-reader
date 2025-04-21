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
    suspend fun getPosts(keyword: String): List<Post> =
        coroutineScope {
            val woowahanPostList = async { woowahanPostService.getPosts(keyword) }
            val naverPostList = async { naverPostService.getPosts(keyword) }
            val totalList = woowahanPostList.await() + naverPostList.await()

            totalList
                .sortedByDescending { it.date }
                .take(min(10, totalList.size))
        }

    fun printInputMessage() {
        rssView.printInputMessage()
    }

    fun printPosts(postList: List<Post>) {
        rssView.printPostList(postList)
    }

    fun readInputContent(): String {
        return rssView.readInputContent()
    }
}
