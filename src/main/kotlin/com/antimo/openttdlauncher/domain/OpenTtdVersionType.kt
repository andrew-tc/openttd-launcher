package com.antimo.openttdlauncher.domain

enum class OpenTtdVersionType {
    STABLE, BETA, NIGHTLY, UNKNOWN;

    companion object {
        fun getTypeFromVersion(version: String): OpenTtdVersionType {
            return when {
                version.startsWith("r") -> NIGHTLY
                version.contains("RC") -> BETA
                version.contains(".") -> STABLE
                else -> UNKNOWN
            }
        }
    }
}
