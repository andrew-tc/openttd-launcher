package com.antimo.openttdlauncher.data.versionchecker

import com.antimo.openttdlauncher.domain.OpenTtdVersion
import io.reactivex.Single

@Suppress("unused")
interface OpenTtdVersionChecker {
    fun check(): OpenTtdVersion
    fun checkAsync() = Single.fromCallable { check() }!!
}