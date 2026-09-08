package com.juandgaines.testground

import android.R.attr.name
import com.google.common.truth.Truth
import com.juandgaines.testground.domain.Coordinates
import com.juandgaines.testground.domain.ExperienceCalculator
import com.juandgaines.testground.domain.Place
import org.junit.Before
import org.junit.Test

class ExperienceCalculatorTest {

    private lateinit var experienceCalculator: ExperienceCalculator

    @Before
    fun setUp() {
        experienceCalculator = ExperienceCalculator()
    }

    @Test
    fun givenTouristSpot_whenCalculatorExperience_thenReturn5Points() {
        //given
        val touristSpot = Place(
            id = "1",
            name = "Times Square",
            coordinates = Coordinates(latitude = 40.7, longitude = -73.5)
        )

        //act
        val result = experienceCalculator.calculateExperience(listOf(touristSpot))

        //assert
        Truth.assertThat(result).isEqualTo(5)
    }

    @Test
    fun givenNoSpot_whenCalculatorExperience_thenReturn0Points() {
        val result = experienceCalculator.calculateExperience(emptyList())

        Truth.assertThat(result).isEqualTo(0)
    }

    @Test
    fun givenCultural_whenCalculatorExperience_thenReturn4Points() {
        val culturalSpot = Place(
            id = "6",
            name = "Cultural Spot",
            coordinates = Coordinates(38.5, -76.5)
        )

        val result = experienceCalculator.calculateExperience(listOf(culturalSpot))

        Truth.assertThat(result).isEqualTo(4)
    }

    @Test
    fun givenMultiplePlaces_whenCalculatorExperience_thenReturnSumOfScores() {
        val places = listOf(
            Place(id = "3", name = "NatureArea", coordinates = Coordinates(36.5, -118.8)),
            Place(id = "4", name = "LocalArea", coordinates = Coordinates(42.6, -70.5)),
            Place(id = "5", name = "Unknown place", coordinates = Coordinates(0.0, 0.0))
        )

        val result = experienceCalculator.calculateExperience(places)

        Truth.assertThat(result).isEqualTo(6)
    }
}