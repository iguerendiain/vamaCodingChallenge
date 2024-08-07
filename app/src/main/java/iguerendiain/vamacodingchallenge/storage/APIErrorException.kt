package iguerendiain.vamacodingchallenge.storage

data class APIErrorException(
    val apiErrorInfo: APIErrorInfo
): Exception()