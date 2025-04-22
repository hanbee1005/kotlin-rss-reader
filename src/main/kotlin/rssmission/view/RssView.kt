package rssmission.view

import rssmission.model.Post

class RssView {
    fun printInputMessage() {
        println("검색어를 입력하세요 (없으면 전체 출력): ")
    }

    fun printPostList(postList: List<Post>) {
        postList.forEachIndexed { index, post ->
            println("[${index + 1}] ${post.title} (${post.date}) - ${post.link} - ${post.company}")
        }
        println()
    }

    fun printNewPostList(postList: List<Post>) {
        println("새로운 글이 등록되었습니다!")
        postList.forEachIndexed { _, post ->
            println("[NEW] ${post.title} (${post.date}) - ${post.link} - ${post.company}")
        }
        println()
    }

    fun readInputContent(): String {
        return readLine() ?: ""
    }
}
