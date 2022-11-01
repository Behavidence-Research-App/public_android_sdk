package com.behavidence.clientsdkapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behavidence.android.sdk_internal.data.model.ResearchQuestionnaire.GroupQuestionnaire.ResearchQuestionnaireGroupResponse
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClient
import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Mhss
import kotlinx.coroutines.Dispatchers
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

                var mhss = ""
                BehavidenceClient.MHSS().getAllMhss(object: BehavidenceCallback<List<Mhss>>{
                    override fun onSuccess(response: List<Mhss>?) {
                        if (response != null) {
                            for(item in response){
                                mhss += "${item.timeInMilli} ${item.date_yyyy_mm_dd} "
                                for(score in item.scores){
                                    mhss += "${score.scoreTitle} ${score.scoreInNumber} ${score.scoring}"
                                }
                                mhss += "\n"
                            }
                        }
                        state = state.copy(
                            mhssHistory = mhss
                        )
                    }

                    override fun onFailure(message: String?) {
                        if (message != null) {
                            Log.d("MhssCheck", message)
                        }
                    }


                })

//                state = state.copy(
//                    mhssHistory = "SuccessFull"
//                )

            } catch (e: Exception) {
                state = state.copy(
                    mhssHistory = "Exception ${e.message}"
                )
            }
        }
    }

    fun researchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    researchQuestions = "Loading"
                )

                var questions = ""
                BehavidenceClient.Questionnaire().getGroupQuestionnaire("saadtest05", object: BehavidenceCallback<ResearchQuestionnaireGroupResponse>{

                    override fun onSuccess(response: ResearchQuestionnaireGroupResponse?) {
                        response?.data?.codeInfo?.questionsGroup?.forEach {
                            it.questions.forEach { q ->
                                questions += q.question + "\n"
                            }
                        }
                        Log.d("ResearchQuestionResponse", "Response $questions")
                        state = state.copy(
                            researchQuestions = questions
                        )
                    }

                    override fun onFailure(message: String?) {
                    }

                })


                Log.d("ResearchQuestionResponse", "Response $questions")


                state = state.copy(
                    researchQuestions = questions
                )

            } catch (e: Exception) {
                state = state.copy(
                    researchQuestions = "Exception ${e.message}"
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
    val researchQuestions: String = "",
    val associationResearch: String = "",
)