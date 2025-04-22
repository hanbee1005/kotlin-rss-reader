package rss.service

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import rss.model.Post
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory

class WoowahanPostService {
    companion object {
        const val WOOWAHAN_RSS_URL: String = "https://techblog.woowahan.com/feed"
        const val WOOWAHAN_COMPANY_NAME: String = "woowahan"
    }

    suspend fun getPosts(): List<Post> {
        val items = getItems()

        val postList: MutableList<Post> = mutableListOf()

        val inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        for (i in 0 until items.length) {
            val item = items.item(i) as Element

            val title = item.getElementsByTagName("title").item(0).textContent
            val link = item.getElementsByTagName("link").item(0).textContent
            val pubDateString = item.getElementsByTagName("pubDate").item(0).textContent
            val pubDate = ZonedDateTime.parse(pubDateString, inputFormatter).format(outputFormatter)

            postList.add(Post(title, link, pubDate, WOOWAHAN_COMPANY_NAME))
        }

        return postList
    }

    private fun getItems(): NodeList {
        val factory = DocumentBuilderFactory.newInstance()
        val xml = factory.newDocumentBuilder().parse(WOOWAHAN_RSS_URL)
        val channel = xml.getElementsByTagName("channel").item(0) as Element

        return channel.getElementsByTagName("item")
    }
}
