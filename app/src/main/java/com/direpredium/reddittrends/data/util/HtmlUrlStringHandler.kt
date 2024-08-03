package com.direpredium.reddittrends.data.util

object HtmlUrlStringHandler {

    fun decodeHtmlEntities(encodedUrl: String): String {
        val symbols = mapOf(
            "&amp;" to "&",
            "&lt;" to "<",
            "&gt;" to ">",
            "&quot;" to "\"",
            "&apos;" to "'",
            "&nbsp;" to " "
        )

        var decodedUrl = encodedUrl
        symbols.forEach { (htmlSymbol, replacedSymbol) ->
            decodedUrl = decodedUrl.replace(htmlSymbol, replacedSymbol)
        }
        return decodedUrl
    }

}