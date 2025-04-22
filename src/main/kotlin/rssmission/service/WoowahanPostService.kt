package rssmission.service

import org.w3c.dom.Element
import rssmission.model.Post
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory

class WoowahanPostService {
    suspend fun getPosts(): List<Post> {
        val factory = DocumentBuilderFactory.newInstance()
        val xml =
            factory.newDocumentBuilder()
                .parse("https://techblog.woowahan.com/feed")
        val channel = xml.getElementsByTagName("channel").item(0) as Element

        val items = channel.getElementsByTagName("item")

        val postList: MutableList<Post> = mutableListOf()

        for (i in 0 until items.length) {
            val item = items.item(i) as Element
            val title = item.getElementsByTagName("title").item(0).textContent
            val link = item.getElementsByTagName("link").item(0).textContent

            val pubDateString = item.getElementsByTagName("pubDate").item(0).textContent
            val inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val parsedDate = ZonedDateTime.parse(pubDateString, inputFormatter)
            val pubDate = parsedDate.format(outputFormatter)

            postList.add(Post(title, link, pubDate, "woowahan"))
        }

        return postList
    }
}
