package com.jn.wadget

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

