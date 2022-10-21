package com.behavidence.clientsdkapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel: ViewModel() {

    var state by mutableStateOf(UIState())

    fun getAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    authInfo = "Loading"
                )

                state = state.copy(
                    authInfo = "SuccessFull"
                )

            } catch (e: Exception) {
                state = state.copy(
                    authInfo = "Exception ${e.message}"
                )
            }
        }
    }
    fun journalUpload() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    journalUpload = "Loading"
                )

                state = state.copy(
                    journalUpload = "SuccessFull"
                )

            } catch (e: Exception) {
                state = state.copy(
                    journalUpload = "Exception ${e.message}"
                )
            }
        }
    }
    fun authRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    authRefresh = "Loading"
                )

                state = state.copy(
                    authRefresh = "SuccessFull"
                )

            } catch (e: Exception) {
                state = state.copy(
                    authRefresh = "Exception ${e.message}"
                )
            }
        }
    }
    fun mhss() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    mhss = "Loading"
                )

                state = state.copy(
                    mhss = "SuccessFull"
                )

            } catch (e: Exception) {
                state = state.copy(
                    mhss = "Exception ${e.message}"
                )
            }
        }
    }
    fun mhssHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    mhssHistory = "Loading"
                )

                state = state.copy(
                    mhssHistory = "SuccessFull"
                )

            } catch (e: Exception) {
                state = state.copy(
                    mhssHistory = "Exception ${e.message}"
                )
            }
        }
    }
    fun associationResearch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    associationResearch = "Loading"
                )

                state = state.copy(
                    associationResearch = "SuccessFull"
                )

            } catch (e: Exception) {
                state = state.copy(
                    associationResearch = "Exception ${e.message}"
                )
            }
        }
    }
}

data class UIState(
    val authInfo: String = "",
    val authRefresh: String = "",
    val journalUpload: String = "",
    val mhss: String = "",
    val mhssHistory: String = "",
    val associationResearch: String = "",
)