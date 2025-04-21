package rssmission.controller

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
    fun getPosts(keyword: String): List<Post> {
        val woowahanPostList = woowahanPostService.getPosts(keyword)
        val naverPostList = naverPostService.getPosts(keyword)
        val totalList = woowahanPostList + naverPostList
        return totalList
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
