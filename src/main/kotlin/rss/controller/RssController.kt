package rss.controller

import rss.model.Post
import rss.service.NaverPostService
import rss.service.WoowahanPostService
import kotlin.math.min

class RssController(
    val woowahanPostService: WoowahanPostService,
    val naverPostService: NaverPostService,
) {
    fun getPosts(keyword: String): List<Post> {
        val woowahanPosts = woowahanPostService.getPosts(keyword)
        val naverPostService = naverPostService.getPosts(keyword)

        val totalPosts = (woowahanPosts + naverPostService).sortedByDescending { it.pubDate }
        return totalPosts.take(min(10, totalPosts.size))
    }
}
