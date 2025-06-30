package com.example.designsystemtemplate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun appTheme(appContent: () -> Unit)