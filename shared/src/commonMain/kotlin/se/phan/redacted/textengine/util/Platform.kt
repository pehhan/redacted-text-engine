package se.phan.redacted.textengine.util

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform