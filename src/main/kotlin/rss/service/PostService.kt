package rss.service

import rss.model.Post

interface PostService {
    suspend fun getPosts(): List<Post>
}
