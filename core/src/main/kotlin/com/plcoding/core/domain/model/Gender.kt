package com.plcoding.core.domain.model

sealed class Gender(val name: String) {
    object Male: Gender("male")
    object Female: Gender("female")

    companion object {
        private val map by lazy { listOf(Male, Female).associateBy(Gender::name) }

        fun fromString(name: String): Gender {
            return map[name.lowercase()] ?: Female
        }
    }
}
