@file:Suppress("unused")

package com.antimo.openttdlauncher.data.downloader

import com.antimo.openttdlauncher.domain.OpenTtdOs
import com.antimo.openttdlauncher.domain.OpenTtdVersion
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import io.reactivex.Single
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths

abstract class OpenTtdDownloader(
    internal val version: OpenTtdVersion,
    internal val os: OpenTtdOs
) {
    interface DownloadCallback {
        fun progress(readBytes: Long, totalBytes: Long)
        fun response(result: Triple<Request, Response, Result<ByteArray, FuelError>>)
    }

    /**
     * Return the base URI for convenience (e.g. mirror link)
     */
    abstract fun getUri(): URI

    /**
     * Return the final, already-resolved URI to be downloaded
     */
    open fun getResolvedUri() = Fuel.get(getUri().toString()).response().second.url.toURI()!!

    @Suppress("MemberVisibilityCanBePrivate")
    fun download(targetFilepath: File? = null, targetFilename: File? = null, callback: DownloadCallback? = null): File {
        val finalTargetFilepath = targetFilepath ?: Paths.get("").toAbsolutePath().toFile()
        if (!finalTargetFilepath.exists()) Files.createDirectories(finalTargetFilepath.toPath())
        if (!finalTargetFilepath.canWrite()) throw IOException()

        val finalTargetFilename = targetFilename ?: Paths.get(getUri().path).fileName.toFile()
        val finalTargetFile = File(finalTargetFilepath, finalTargetFilename.name)

        val request = Fuel.download(getResolvedUri().toString())
            .destination { _, _ -> finalTargetFile }

        if (callback != null) {
            request.progress { readBytes: Long, totalBytes: Long -> callback.progress(readBytes, totalBytes) }
            callback.response(request.response())
        } else {
            request.response()
        }

        return finalTargetFile
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun downloadAsync(
        targetFilepath: File? = null,
        targetFilename: File? = null,
        callback: DownloadCallback? = null
    ): Single<File> {
        return Single.fromCallable { download(targetFilepath, targetFilename, callback) }
    }
}