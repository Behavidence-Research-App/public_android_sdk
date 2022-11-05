package com.behavidence.clientsdkapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceCallback
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClient
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceResearchCallback
import com.behavidence.android.sdk_internal.domain.model.MHSS.interfaces.Mhss
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.AssociationParticipation
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.CodeInfo
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.Participation
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchParticipation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    var state by mutableStateOf(UIState())
    var codeInfo: CodeInfo? = null

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
                BehavidenceClient.MHSS().getAllMhss(object : BehavidenceCallback<List<Mhss>> {
                    override fun onSuccess(response: List<Mhss>?) {
                        if (response != null) {
                            for (item in response) {
                                mhss += "${item.timeInMilli} ${item.date_yyyy_mm_dd} "
                                for (score in item.scores) {
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

    fun mhssSingleHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    mhssDate = "Loading"
                )

                BehavidenceClient.MHSS().getMhss("2022-11-01", object : BehavidenceCallback<Mhss> {
                    override fun onSuccess(response: Mhss?) {
                        var mhss = ""
                        if (response != null) {
                                mhss += "${response.timeInMilli} ${response.date_yyyy_mm_dd} "
                                for (score in response.scores) {
                                    mhss += "${score.scoreTitle} ${score.scoreInNumber} ${score.scoring}"
                                }
                                mhss += "\n"
                            }
                        state = state.copy(
                            mhssDate = mhss
                        )
                    }

                    override fun onFailure(message: String?) {
                        if (message != null) {
                            Log.d("MhssCheck", message)
                        }
                    }


                })

            } catch (e: Exception) {
                state = state.copy(
                    mhssDate = "Exception ${e.message}"
                )
            }
        }
    }

    fun mhssDateRangeHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    mhssDateRange = "Loading"
                )

                var mhss = ""
                BehavidenceClient.MHSS().getMhssInDateRange("2022-10-29", "2022-11-02", object : BehavidenceCallback<List<Mhss>> {
                    override fun onSuccess(response: List<Mhss>?) {
                        if (response != null) {
                            for (item in response) {
                                mhss += "${item.timeInMilli} ${item.date_yyyy_mm_dd} "
                                for (score in item.scores) {
                                    mhss += "${score.scoreTitle} ${score.scoreInNumber} ${score.scoring}"
                                }
                                mhss += "\n"
                            }
                        }
                        state = state.copy(
                            mhssDateRange = mhss
                        )
                    }

                    override fun onFailure(message: String?) {
                        if (message != null) {
                            Log.d("MhssCheck", message)
                        }
                    }


                })

            } catch (e: Exception) {
                state = state.copy(
                    mhssDateRange = "Exception ${e.message}"
                )
            }
        }
    }

    fun mhssTimeRangeHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    mhssTimeRange = "Loading"
                )

                var mhss = ""
                BehavidenceClient.MHSS().getMhssInTimeRange(1656975794543, 1667475845064, object : BehavidenceCallback<List<Mhss>> {
                    override fun onSuccess(response: List<Mhss>?) {
                        if (response != null) {
                            for (item in response) {
                                mhss += "${item.timeInMilli} ${item.date_yyyy_mm_dd} "
                                for (score in item.scores) {
                                    mhss += "${score.scoreTitle} ${score.scoreInNumber} ${score.scoring}"
                                }
                                mhss += "\n"
                            }
                        }
                        state = state.copy(
                            mhssTimeRange = mhss
                        )
                    }

                    override fun onFailure(message: String?) {
                        if (message != null) {
                            Log.d("MhssCheck", message)
                        }
                    }


                })

            } catch (e: Exception) {
                state = state.copy(
                    mhssTimeRange = "Exception ${e.message}"
                )
            }
        }
    }

    fun researchQuestions(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    researchQuestions = "Loading"
                )

                BehavidenceClient.Participation()
                    .getCodeInfo(code, object : BehavidenceResearchCallback<CodeInfo> {
                        override fun onSuccess(
                            response: CodeInfo?,
                            codeType: BehavidenceResearchCallback.CodeType?
                        ) {

                            response?.let {

                                if (codeType == BehavidenceResearchCallback.CodeType.ASSOCIATION || codeType == BehavidenceResearchCallback.CodeType.RESEARCH_ASSOCIATION)
                                    it.setAssociationTimeInSec(999999)

                                if (codeType == BehavidenceResearchCallback.CodeType.RESEARCH || codeType == BehavidenceResearchCallback.CodeType.RESEARCH_ASSOCIATION){
                                    for(question in response.questionGroups){
                                        for(q in question.researchQuestions){
                                            if(q.isMultipleChoice) {
                                                var index = 0;
                                                for (opt in q.selectionOptions)
                                                    q.selectOptionIndex(index++, (0..1).random() == 1)
                                            }else{
                                                q.selectOptionIndex((0 until q.selectionOptions.size).random(), true)
                                            }
                                        }
                                    }
                                }

                                Log.d("GetCodeInfoResponse", it.toString())

                                state = state.copy(
                                    researchQuestions = it.toString()
                                )

                                codeInfo = response
                            }

                            Log.d("ResearchQuestionResponse", "Full")

                        }

                        override fun onFailure(message: String?) {
                            message?.let { Log.d("GetCodeInfoError", it) }

                            Log.d("ResearchQuestionResponse", "Failed")
                        }

                    })


            } catch (e: Exception) {
                state = state.copy(
                    researchQuestions = "Exception ${e.message}"
                )
            }
        }
    }

    fun participateResearch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    participateResearch = "Loading"
                )

                codeInfo?.let {
                    BehavidenceClient.Participation()
                        .submit(it, object : BehavidenceCallback<String> {
                            override fun onSuccess(response: String?) {
                                codeInfo = null
                                if (response != null) {
                                    Log.d("ResearcParticipation", response)
                                }
                            }

                            override fun onFailure(message: String?) {
                                if (message != null) {
                                    Log.d("ResearcParticipation", message)
                                }
                            }
                        })

                } ?: kotlin.run {
                    Log.d("ParticipateResearch", "Code info is empty")
                }

                state = state.copy(
                    participateResearch = "SuccessFull"
                )

            } catch (e: Exception) {
                state = state.copy(
                    participateResearch = "Exception ${e.message}"
                )
            }
        }
    }

    fun getParticipation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    allParticipation = "Loading"
                )

                BehavidenceClient.Participation()
                    .getAllParticipation(object : BehavidenceCallback<List<Participation>> {
                        override fun onSuccess(response: List<Participation>?) {
                            state = state.copy(
                                allParticipation = response.toString()
                            )
                        }

                        override fun onFailure(message: String?) {
                            state = state.copy(
                                allParticipation = "Failed"
                            )
                        }

                    })


            } catch (e: Exception) {
                state = state.copy(
                    allParticipation = "Exception ${e.message}"
                )
            }
        }
    }

    fun getResearchParticipation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    allResearchParticipation = "Loading"
                )

                BehavidenceClient.Participation().getAllResearchParticipation(object :
                    BehavidenceCallback<List<ResearchParticipation>> {
                    override fun onSuccess(response: List<ResearchParticipation>?) {
                        state = state.copy(
                            allResearchParticipation = response.toString(),
                            researchList = response
                        )
                    }

                    override fun onFailure(message: String?) {
                        state = state.copy(
                            allResearchParticipation = "Failed"
                        )
                    }

                })


            } catch (e: Exception) {
                state = state.copy(
                    allResearchParticipation = "Exception ${e.message}"
                )
            }
        }
    }

    fun getAssociationParticipation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    allAssociationParticipation = "Loading"
                )

                BehavidenceClient.Participation().getAllAssociationParticipation(object :
                    BehavidenceCallback<List<AssociationParticipation>> {
                    override fun onSuccess(response: List<AssociationParticipation>?) {
                        state = state.copy(
                            allAssociationParticipation = response.toString(),
                            associationList = response
                        )
                    }

                    override fun onFailure(message: String?) {
                        state = state.copy(
                            allAssociationParticipation = "Failed"
                        )
                    }

                })


            } catch (e: Exception) {
                state = state.copy(
                    allAssociationParticipation = "Exception ${e.message}"
                )
            }
        }
    }

    fun deleteAssociation(associationParticipation: AssociationParticipation) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                BehavidenceClient.Participation().deleteAssociationParticipation(
                    associationParticipation,
                    object : BehavidenceCallback<String> {
                        override fun onSuccess(response: String?) {

                                Log.d("ResearchDeleteResponseSuccess", response ?: "null")

                        }

                        override fun onFailure(message: String?) {

                                Log.d("ResearchDeleteResponseFailed", message ?: "null")

                        }

                    })

            } catch (e: Exception) {

            }
        }
    }

    fun deleteResearch(researchParticipation: ResearchParticipation) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                BehavidenceClient.Participation().deleteResearchParticipation(
                    researchParticipation,
                    object : BehavidenceCallback<String> {
                        override fun onSuccess(response: String?) {

                            Log.d("ResearchDeleteResponse", response ?: "null")

                        }

                        override fun onFailure(message: String?) {
                            if (message != null) {
                                Log.d("ResearchDeleteResponseFailed", message ?: "null")
                            }
                        }

                    })

            } catch (e: Exception) {

            }
        }
    }

    fun associationResearch(){
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

    fun getAllJournals() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    allJournals = "Loading"
                )

                var li = ""
                BehavidenceClient.Journal().allJournals.forEach {
                    li += it.toString() + "\n"
                }

                state = state.copy(
                    allJournals = li
                )

            } catch (e: Exception) {
                state = state.copy(
                    allJournals = "Exception ${e.message}"
                )
            }
        }
    }

    fun getSingleJournal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    singleJournal = "Loading"
                )

                val journal = BehavidenceClient.Journal().getJournal(123123)
                journal?.let {
                    state = state.copy(
                        singleJournal = it.toString()
                    )
                }



            } catch (e: Exception) {
                state = state.copy(
                    singleJournal = "Exception ${e.message}"
                )
            }
        }
    }

    fun getJournalsInRange() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    dateRangeJournal = "Loading"
                )

                var li = ""
                BehavidenceClient.Journal().getJournalInTimeRange(1667495948812,1667545882672).forEach {
                    li += it.toString() + "\n"
                }

                state = state.copy(
                    dateRangeJournal = li
                )


            } catch (e: Exception) {
                state = state.copy(
                    dateRangeJournal = "Exception ${e.message}"
                )
            }
        }
    }

    fun saveJournal(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state = state.copy(
                    saveJournal = "Loading"
                )


                BehavidenceClient.Journal().saveJournal(text, System.currentTimeMillis())

                state = state.copy(
                    saveJournal = "Successful"
                )


            } catch (e: Exception) {
                state = state.copy(
                    saveJournal = "Exception ${e.message}"
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
    val mhssDate: String = "",
    val mhssDateRange: String = "",
    val mhssTimeRange: String = "",
    val researchQuestions: String = "",
    val participateResearch: String = "",
    val allParticipation: String = "",
    val allResearchParticipation: String = "",
    val researchList: List<ResearchParticipation>? = null,
    val allAssociationParticipation: String = "",
    val associationList: List<AssociationParticipation>? = null,
    val allJournals: String = "",
    val singleJournal: String = "",
    val dateRangeJournal: String = "",
    val saveJournal: String = "",
    val associationResearch: String = "",
)