package com.plcoding.tracker_presentation.search

import com.plcoding.tracker.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = "",
)
