package rss.controller

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import rss.model.Post
import rss.service.PostService

class RssController(
    private val postServices: List<PostService>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun getPosts(): List<Post> =
        coroutineScope {
            postServices
                .map { async { it.getPosts() } }
                .awaitAll()
                .flatten()
        }

    fun comparePosts(
        originPosts: List<Post>,
        newPosts: List<Post>,
    ): List<Post> {
        return newPosts.filter { !originPosts.contains(it) }
    }
}
