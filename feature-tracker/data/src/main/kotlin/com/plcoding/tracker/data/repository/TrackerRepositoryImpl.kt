package com.plcoding.tracker.data.repository

import com.plcoding.tracker.data.local.TrackerDao
import com.plcoding.tracker.data.mapper.toTrackableFood
import com.plcoding.tracker.data.mapper.toTrackedFood
import com.plcoding.tracker.data.mapper.toTrackedFoodEntity
import com.plcoding.tracker.data.remote.OpenFoodApi
import com.plcoding.tracker.domain.model.CaloriesPerGram
import com.plcoding.tracker.domain.model.TrackableFood
import com.plcoding.tracker.domain.model.TrackedFood
import com.plcoding.tracker.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi,
): TrackerRepository {

    companion object {
        const val LOWER_BOUND_RATE = 0.99f
        const val UPPER_BOUND_RATE = 1.01f
    }

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize,
            )
            Result.success(
                searchDto.products
                    .filter {
                        val calculatedCalories =
                            it.nutriments.carbohydrates100g * CaloriesPerGram.CARBS +
                                it.nutriments.proteins100g * CaloriesPerGram.PROTEIN +
                                    it.nutriments.fat100g * CaloriesPerGram.FAT
                        val lowerBound = calculatedCalories * LOWER_BOUND_RATE
                        val upperBound = calculatedCalories * UPPER_BOUND_RATE
                        it.nutriments.energyKcal100g in  (lowerBound..upperBound)
                    }
                    .mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year,
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}
