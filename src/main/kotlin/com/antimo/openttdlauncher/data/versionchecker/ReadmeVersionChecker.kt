package com.antimo.openttdlauncher.data.versionchecker

import com.antimo.openttdlauncher.domain.OpenTtdVersion
import java.io.File
import java.io.FileNotFoundException
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*


@Suppress("unused")
class ReadmeVersionChecker(private val file: File) :
    OpenTtdVersionChecker {
    override fun check(): OpenTtdVersion {
        if (!file.exists()) throw FileNotFoundException()

        var version = "unknown"
        var date = Date(0).toInstant()

        file.forEachLine { line ->
            if (line.startsWith("Last updated:")) {
                val dateString = line.substring(line.lastIndexOf(":") + 1).trim()
                date = getInstant(dateString)
            } else if (line.startsWith("Release version:")) {
                version = line.substring(line.lastIndexOf(":") + 1).trim()
            }
        }

        return OpenTtdVersion(version, date)
    }

    private fun getInstant(text: String): Instant {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"))
        val date = dateTimeFormatter.parse("$text 23:59:59") // hack for time to always be later
        return Instant.from(date)
    }
}