package iguerendiain.vamacodingchallenge.model

data class Feed<T>(
    val title: String? = null,
    val id: String? = null,
    val author: Author? = null,
    val links: List<Links> = listOf(),
    val copyright: String,
    val country: String? = null,
    val icon: String? = null,
    val updated: String? = null,
    val results: List<T> = listOf()
)
