package com.antimo.openttdlauncher.data.installer

import io.reactivex.Single
import java.io.File

@Suppress("unused")
abstract class OpenTtdInstaller(internal val sourceFile: File, internal val targetFilepath: File? = null) {

    abstract fun install(): Boolean
    fun installAsync() = Single.fromCallable { install() }!!
}