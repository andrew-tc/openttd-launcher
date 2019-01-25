package com.antimo.openttdlauncher.data.downloader

import com.antimo.openttdlauncher.domain.OpenTtdOs
import com.antimo.openttdlauncher.domain.OpenTtdVersion
import com.antimo.openttdlauncher.domain.OpenTtdVersionType
import java.net.URI

private const val BASE_URL = "https://binaries.openttd.org"

@Suppress("unused")
class OpenTtdWebsiteDownloader(version: OpenTtdVersion, os: OpenTtdOs) : OpenTtdDownloader(version, os) {

    override fun getUri(): URI {
        val version = this.version.version
        return URI(
            BASE_URL +
                    "/${getPathForVersionType()}" +
                    "/$version" +
                    "/openttd-${getPathForVersion()}-${getPathForOs()}.${getFileExtensionForOs()}"
        )
    }

    private fun getPathForVersionType(): String? {
        return when (version.type) {
            OpenTtdVersionType.STABLE -> "releases"
            OpenTtdVersionType.BETA -> TODO("path for beta version type")
            OpenTtdVersionType.NIGHTLY -> "nightlies/trunk"
            else -> null
        }
    }

    private fun getPathForVersion(): String? {
        val version = version.version

        return when (this.version.type) {
            OpenTtdVersionType.STABLE -> version
            OpenTtdVersionType.BETA -> TODO("path for beta version")
            OpenTtdVersionType.NIGHTLY -> "trunk-$version"
            else -> null
        }
    }

    private fun getPathForOs(): String {
        return when (os) {
            OpenTtdOs.WINDOWS_WIN32 -> "windows-win32"
            OpenTtdOs.WINDOWS_WIN64 -> "windows-win64"
            OpenTtdOs.WINDOWS_WIN9X -> "windows-win9x"

            OpenTtdOs.MACOSX_UNIVERSAL -> "macosx-universal"

            OpenTtdOs.LINUX_GENERIC_I686 -> "linux-generic-i686"
            OpenTtdOs.LINUX_GENERIC_AMD64 -> "linux-generic-amd64"
            OpenTtdOs.LINUX_DEBIAN_JESSIE_I386 -> "linux-debian-jessie-i386"
            OpenTtdOs.LINUX_DEBIAN_JESSIE_AMD64 -> "linux-debian-jessie-amd64"
            OpenTtdOs.LINUX_DEBIAN_WHEEZY_I386 -> "linux-debian-wheezy-i386"
            OpenTtdOs.LINUX_DEBIAN_WHEEZY_AMD64 -> "linux-debian-wheezy-amd64"
            OpenTtdOs.LINUX_UBUNTU_TRUSTY_I386 -> "linux-ubuntu-trusty-i386"
            OpenTtdOs.LINUX_UBUNTU_TRUSTY_AMD64 -> "linux-ubuntu-trusty-amd64"
        }
    }

    private fun getFileExtensionForOs(): String {
        return when (os) {
            OpenTtdOs.WINDOWS_WIN32,
            OpenTtdOs.WINDOWS_WIN64,
            OpenTtdOs.WINDOWS_WIN9X -> "zip" // or "exe"

            OpenTtdOs.MACOSX_UNIVERSAL -> "zip"

            OpenTtdOs.LINUX_GENERIC_I686,
            OpenTtdOs.LINUX_GENERIC_AMD64 -> "tar.xz" // or "tar.gz"

            OpenTtdOs.LINUX_DEBIAN_JESSIE_I386,
            OpenTtdOs.LINUX_DEBIAN_JESSIE_AMD64,
            OpenTtdOs.LINUX_DEBIAN_WHEEZY_I386,
            OpenTtdOs.LINUX_DEBIAN_WHEEZY_AMD64,
            OpenTtdOs.LINUX_UBUNTU_TRUSTY_I386,
            OpenTtdOs.LINUX_UBUNTU_TRUSTY_AMD64 -> "deb"
        }
    }
}