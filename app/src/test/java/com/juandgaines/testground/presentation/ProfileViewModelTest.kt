package com.juandgaines.testground.presentation

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.jvm.Throws

class ProfileViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ProfileViewModel
    private lateinit var repository: UserRepositoryFake

    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)

        repository = UserRepositoryFake()

        viewModel = ProfileViewModel(
            repository = repository,
            savedStateHandle = SavedStateHandle(
                initialState = mapOf(
                    "userId" to repository.profileToReturn.user.id
                )
            )
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenValidUserId_whenLoadProfile_thenProfileIsLoaded() = runTest {
        //act
        viewModel.loadProfile()

        //assert
        Truth.assertThat(viewModel.state.value.profile).isEqualTo(repository.profileToReturn)
        Truth.assertThat(viewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun givenRepositoryError_whenLoadProfile_thenErrorStateIsSet() = runTest {
        //arrange
        repository.errorToReturn = Exception("Test exception")

        //act
        viewModel.loadProfile()

        //assert
        Truth.assertThat(viewModel.state.value.profile).isNull()
        Truth.assertThat(viewModel.state.value.errorMessage).isEqualTo("Test exception")
        Truth.assertThat(viewModel.state.value.isLoading).isFalse()
    }
}