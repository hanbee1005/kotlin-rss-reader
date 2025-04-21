package rssmission.controller

import rssmission.model.Post
import rssmission.service.WoowahanPostService
import rssmission.view.RssView

class RssController(
    val woowahanPostService: WoowahanPostService,
    val rssView: RssView,
) {
    fun getPosts(): List<Post> {
        return woowahanPostService.getPosts()
    }

    fun printPosts(postList: List<Post>) {
        rssView.printPostList(postList)
    }
}
