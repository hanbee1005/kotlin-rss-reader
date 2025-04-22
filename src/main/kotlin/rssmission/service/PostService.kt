package rssmission.service

import rssmission.model.Post

interface PostService {
    suspend fun getPosts(): List<Post>
}
