package rss

import rss.controller.RssController
import rss.service.WoowahanPostService
import rss.view.RssView

fun main() {
    // 1. 컨트롤러, 뷰 생성
    val controller = RssController(WoowahanPostService())
    val view = RssView()

    // 2. 입력 받을 문구 출력
    view.printInputMessage()
    val keyword = view.getReadLine()

    // 3. RSS 데이터 가져오기
    val posts = controller.getPosts()

    // 4. RSS 데이터 출력
    view.printPosts(posts)

    // 5. 시간 비교
}
