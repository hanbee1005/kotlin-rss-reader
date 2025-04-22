package rss.controller

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import rss.model.Post
import rss.service.NaverPostService
import rss.service.WoowahanPostService

class RssController(
    private val woowahanPostService: WoowahanPostService,
    private val naverPostService: NaverPostService,
) {
    suspend fun getPosts(): List<Post> =
        coroutineScope {
            val woowahanPosts = async { woowahanPostService.getPosts() }
            val naverPostService = async { naverPostService.getPosts() }

            woowahanPosts.await() + naverPostService.await()
        }
}
