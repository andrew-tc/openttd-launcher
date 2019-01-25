package com.antimo.openttdlauncher.data.versionchecker

import com.antimo.openttdlauncher.domain.OpenTtdVersion
import com.antimo.openttdlauncher.domain.OpenTtdVersionType
import org.jsoup.Jsoup
import java.time.Instant
import java.time.format.DateTimeFormatter

@Suppress("unused")
class OpenTtdWebsiteVersionChecker(private val type: OpenTtdVersionType) :
    OpenTtdVersionChecker {

    override fun check(): OpenTtdVersion {
        val url = when (type) {
            OpenTtdVersionType.STABLE -> "https://www.openttd.org/en/download-stable"
            OpenTtdVersionType.BETA -> "https://www.openttd.org/en/download-testing"
            OpenTtdVersionType.NIGHTLY -> "https://www.openttd.org/en/download-trunk"
            else -> "https://www.openttd.org/en/download-stable"
        }

        return parseHtml(url)
    }

    private fun parseHtml(url: String): OpenTtdVersion {
        val doc = Jsoup.connect(url).get()
        val text = doc.selectFirst(".section-item:nth-child(4) p").text()
        val data = parseText(text)
        return OpenTtdVersion(data.first, getInstant(data.second))
    }

    private fun parseText(text: String): Pair<String, String> {
        val version = text.substring(text.indexOf("is ") + 3, text.indexOf(","))
        val date = text.substring(text.indexOf("on ") + 3, text.lastIndexOf("."))

        return Pair(version, date)
    }

    private fun getInstant(text: String): Instant {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z")
        val date = dateTimeFormatter.parse(text)
        return Instant.from(date)
    }
}