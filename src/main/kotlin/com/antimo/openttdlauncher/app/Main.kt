package com.antimo.openttdlauncher.app

import com.antimo.openttdlauncher.data.versionchecker.OpenTtdWebsiteVersionChecker
import com.antimo.openttdlauncher.domain.OpenTtdVersionType

fun main(args: Array<String>) {
    OpenTtdWebsiteVersionChecker(OpenTtdVersionType.STABLE).check().also { openTtdVersion -> println(openTtdVersion) }
}