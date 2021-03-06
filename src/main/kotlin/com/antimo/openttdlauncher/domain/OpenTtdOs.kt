package com.antimo.openttdlauncher.domain

enum class OpenTtdOs {
    WINDOWS_WIN32, WINDOWS_WIN64, WINDOWS_WIN9X,

    MACOSX_UNIVERSAL,

    LINUX_GENERIC_I686, LINUX_GENERIC_AMD64,
    LINUX_DEBIAN_JESSIE_I386, LINUX_DEBIAN_JESSIE_AMD64,
    LINUX_DEBIAN_WHEEZY_I386, LINUX_DEBIAN_WHEEZY_AMD64,
    LINUX_UBUNTU_TRUSTY_I386, LINUX_UBUNTU_TRUSTY_AMD64,
}