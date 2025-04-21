package rss.model

data class Post(
    val title: String,
    val link: String,
    val pubDate: String,
    val company: String,
) {
    fun getInfo(): String {
        return "$title ($pubDate) - $link (from. $company)"
    }
}
