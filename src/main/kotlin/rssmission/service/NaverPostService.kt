package rssmission.service

import org.w3c.dom.Element
import rssmission.model.Post
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

class NaverPostService : PostService {
    override suspend fun getPosts(): List<Post> {
        val factory = DocumentBuilderFactory.newInstance()
        val xml =
            factory.newDocumentBuilder()
                .parse("https://d2.naver.com/d2.atom")
        val feed = xml.getElementsByTagName("feed").item(0) as Element

        val entry = feed.getElementsByTagName("entry")

        val postList: MutableList<Post> = mutableListOf()

        for (i in 0 until entry.length) {
            val item = entry.item(i) as Element
            val title = item.getElementsByTagName("title").item(0).textContent
            val link = (item.getElementsByTagName("link").item(0) as Element).getAttribute("href")

            val pubDateString = item.getElementsByTagName("updated").item(0).textContent
            val parsedDate = ZonedDateTime.parse(pubDateString)
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val pubDate = parsedDate.format(outputFormatter)

            postList.add(Post(title, link, pubDate, "naver"))
        }

        return postList
    }
}
