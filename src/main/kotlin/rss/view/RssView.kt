package rss.view

import rss.model.Post

class RssView {
    fun printInputMessage() {
        println("검색어를 입력하세요 (없으면 전체 출력):")
    }

    fun getReadLine(): String {
        return readlnOrNull() ?: ""
    }

    fun printPosts(posts: List<Post>) {
        posts.forEachIndexed { index, it ->
            println("[$index] ${it.getInfo()}")
        }
    }
}
