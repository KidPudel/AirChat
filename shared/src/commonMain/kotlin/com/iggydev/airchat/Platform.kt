package com.iggydev.airchat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform