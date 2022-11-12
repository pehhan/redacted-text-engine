package se.phan.redacted.textengine

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform