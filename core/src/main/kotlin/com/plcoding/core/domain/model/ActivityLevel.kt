package com.plcoding.core.domain.model

sealed class ActivityLevel(val name: String) {
    object Low: ActivityLevel("low")
    object Medium: ActivityLevel("medium")
    object High: ActivityLevel("high")

    companion object {
        private val map by lazy { listOf(Low, Medium, High).associateBy(ActivityLevel::name) }

        fun fromString(name: String): ActivityLevel {
            return map[name.lowercase()] ?: Medium
        }
    }
}
