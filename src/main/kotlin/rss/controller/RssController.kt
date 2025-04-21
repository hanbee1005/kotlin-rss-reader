package rss.controller

import rss.model.Post
import rss.service.WoowahanPostService

class RssController(
    val woowahanPostService: WoowahanPostService,
) {
    fun getPosts(): List<Post> {
        return woowahanPostService.getPosts()
    }
}
