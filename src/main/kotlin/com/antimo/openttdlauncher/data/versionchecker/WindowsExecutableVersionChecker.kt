package com.antimo.openttdlauncher.data.versionchecker

import com.antimo.openttdlauncher.domain.OpenTtdVersion
import org.boris.pecoff4j.constant.ResourceType
import org.boris.pecoff4j.io.PEParser
import org.boris.pecoff4j.io.ResourceParser
import org.boris.pecoff4j.util.ResourceHelper
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.time.Instant

@Suppress("unused")
class WindowsExecutableVersionChecker(private val file: File) :
    OpenTtdVersionChecker {

    override fun check(): OpenTtdVersion {
        if (!file.exists()) throw FileNotFoundException(file.canonicalPath)

        val version = getProductVersion(file.canonicalPath)
        val date = getLastModifiedDate(file.toPath())

        return OpenTtdVersion(version, date)
    }

    private fun getProductVersion(filepath: String): String {
        val pe = PEParser.parse(filepath)
        val rd = pe.imageData.resourceTable

        val entries = ResourceHelper.findResources(rd, ResourceType.VERSION_INFO)
        for (i in entries.indices) {
            val data = entries[i].data
            val versionInfo = ResourceParser.readVersionInfo(data)

            val strings = versionInfo.stringFileInfo
            val table = strings.getTable(0)
            for (j in 0 until table.count) {
                if ("ProductVersion" == table.getString(j).key)
                    return table.getString(j).value
            }
        }
        return "unknown" // dead code, hopefully
    }

    private fun getLastModifiedDate(path: Path): Instant {
        val attr = Files.readAttributes<BasicFileAttributes>(path, BasicFileAttributes::class.java)
        return attr.lastModifiedTime().toInstant()
    }
}