package com.plcoding.core.domain.model

sealed class GoalType(val name: String) {
    object LoseWeight: GoalType("lose_weight")
    object KeepWeight: GoalType("keep_weight")
    object GainWeight: GoalType("gain_weight")

    companion object {
        private val map by lazy { listOf(LoseWeight, KeepWeight, GainWeight).associateBy(GoalType::name) }

        fun fromString(name: String): GoalType {
            return map[name.lowercase()] ?: KeepWeight
        }
    }
}
