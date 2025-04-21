package rss.controller

import kotlinx.coroutines.coroutineScope
import rss.model.Post
import rss.service.NaverPostService
import rss.service.WoowahanPostService
import kotlin.math.min

class RssController(
    private val woowahanPostService: WoowahanPostService,
    private val naverPostService: NaverPostService,
) {
    suspend fun getPosts(keyword: String): List<Post> =
        coroutineScope {
            val woowahanPosts = woowahanPostService.getPosts(keyword)
            val naverPostService = naverPostService.getPosts(keyword)

            val totalPosts = (woowahanPosts + naverPostService).sortedByDescending { it.pubDate }
            totalPosts.take(min(10, totalPosts.size))
        }
}
