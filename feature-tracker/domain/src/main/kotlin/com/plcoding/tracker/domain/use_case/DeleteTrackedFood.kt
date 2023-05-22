package com.plcoding.tracker.domain.use_case

import com.plcoding.tracker.domain.model.TrackedFood
import com.plcoding.tracker.domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(trackedFood: TrackedFood) {
        repository.deleteTrackedFood(trackedFood)
    }
}
