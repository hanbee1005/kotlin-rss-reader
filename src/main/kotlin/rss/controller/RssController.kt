package rss.controller

import rss.model.Post
import rss.service.WoowahanPostService
import kotlin.math.min

class RssController(
    val woowahanPostService: WoowahanPostService,
) {
    fun getPosts(keyword: String): List<Post> {
        val woowahanPosts = woowahanPostService.getPosts(keyword).sortedByDescending { it.pubDate }
        return woowahanPosts.take(min(10, woowahanPosts.size))
    }
}
