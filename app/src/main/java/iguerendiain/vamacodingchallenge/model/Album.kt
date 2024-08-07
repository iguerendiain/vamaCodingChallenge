package iguerendiain.vamacodingchallenge.model

import java.text.DateFormat
import java.text.SimpleDateFormat

data class Album(
    val artistName: String = "",
    val id: String = "",
    val name: String = "",
    val releaseDate: String? = null,
    val kind: String = "",
    val artistId: String = "",
    val artistUrl: String = "",
    val artworkUrl100: String = "",
    val genres: List<Genre> = listOf(),
    val url: String = "",
){
    private val dateParser = SimpleDateFormat("yyyy-MM-dd")

    fun getArtworkURL(width: Int) = artworkUrl100
        .replace("/100x100bb.jpg", "/${width}x${width}bb.jpg")

    fun renderReleaseDate(dateFormat: DateFormat): String? {
        return if (releaseDate!=null){
            if (releaseDate.contains("-")){
                dateParser.parse(releaseDate)?.let { parsedDate ->
                    dateFormat.format(parsedDate)
                }
            }else{
                try{
                    releaseDate
                }catch (e: Exception){
                    null
                }
            }
        }else null
    }

}
