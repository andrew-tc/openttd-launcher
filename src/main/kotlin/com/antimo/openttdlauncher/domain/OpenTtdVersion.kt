package com.antimo.openttdlauncher.domain

import java.time.Instant

data class OpenTtdVersion(
    val version: String,
    val date: Instant,
    val type: OpenTtdVersionType = OpenTtdVersionType.getTypeFromVersion(
        version
    )
) : Comparable<OpenTtdVersion> {
    override fun compareTo(other: OpenTtdVersion): Int {
        //comparing date might not be reliable
        //TODO compare type, then version instead
        return date.compareTo(other.date)
    }
}