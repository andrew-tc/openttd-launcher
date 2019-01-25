package com.antimo.openttdlauncher.data.installer

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipFile

@Suppress("unused")
class ZipInstaller(sourceFile: File, targetFilepath: File? = null, private val callback: ZipInstallerCallback? = null) :
    OpenTtdInstaller(sourceFile, targetFilepath) {

    interface ZipInstallerCallback {
        fun progress(filename: String, extractedFiles: Long, totalFiles: Long)
    }

    override fun install(): Boolean {
        if (!sourceFile.exists()) throw FileNotFoundException()

        val finalTargetFilepath = targetFilepath ?: Paths.get("").toAbsolutePath().toFile()
        if (!finalTargetFilepath.exists()) Files.createDirectories(finalTargetFilepath.toPath())
        if (!finalTargetFilepath.canWrite()) throw IOException()

        var count = 0L
        var total = -1L
        ZipFile(sourceFile).use { zip ->
            total = zip.entries().toList().filter { zipEntry -> !zipEntry.isDirectory }.size.toLong()

            zip.entries().asSequence().forEach { entry ->
                val file = File(finalTargetFilepath, entry.name)
                if (entry.isDirectory) {
                    Files.createDirectories(file.toPath())
                } else {
                    zip.getInputStream(entry).use { input ->
                        file.outputStream().use { output ->
                            input.copyTo(output)
                            count++
                            callback?.progress(entry.name, count, total)
                        }
                    }
                }
            }
        }

        return count == total
    }
}