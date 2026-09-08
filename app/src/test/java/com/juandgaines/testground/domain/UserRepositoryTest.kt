package com.juandgaines.testground.domain

import com.google.common.truth.Truth
import com.juandgaines.testground.data.UserRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var apiFake: UserFakeApiFake

    @Before
    fun setup() {
        apiFake = UserFakeApiFake()
        userRepository = UserRepositoryImpl(apiFake)
    }

    @Test
    fun givenValidUserId_whenProfileWithFakeApi_thenReturnsProfile() = runTest {
        //given
        val userId = "1"

        //act
        val profileResult = userRepository.getProfile(userId)

        //assert
        Truth.assertThat(profileResult.isSuccess).isTrue()
        Truth.assertThat(profileResult.getOrThrow().user.id).isEqualTo("1")

        val expectedPlaces = apiFake.places.filter{ it.id == "1" }
        Truth.assertThat(profileResult.getOrThrow().places).isEqualTo(expectedPlaces)
    }
}