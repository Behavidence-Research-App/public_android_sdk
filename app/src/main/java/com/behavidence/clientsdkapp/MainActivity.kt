package com.behavidence.clientsdkapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue

import androidx.compose.ui.unit.dp

import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClient
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.AssociationParticipation
import com.behavidence.android.sdk_internal.domain.model.Research.interfaces.ResearchParticipation
import com.behavidence.clientsdkapp.ui.theme.CientSDKAppTheme

class MainActivity : ComponentActivity() {
    private val testViewModel by viewModels<TestViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CientSDKAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TestSDK(testViewModel)
                }
            }
        }

//        BehavidenceClient.initialize(this)

    }

    @Composable
    fun TestSDK(
        viewModel: TestViewModel
    ) {
        val scroll = rememberScrollState()
        val label = remember {
            mutableStateOf("")
        }
        val context = LocalContext.current

        val list = listOf(
            TestButton(
                textRequired = false,
                buttonTitle = "Start Dopa One",
                testText = "",
                researches = null,
                associations = null
            ) {
                viewModel.startDopaOne(context)
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Manual Upload Data",
                testText = "",
                researches = null,
                associations = null
            ) {
                viewModel.manualUpload(context)
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Get User Id",
                testText = viewModel.state.userId,
                researches = null,
                associations = null
            ) {
                viewModel.getUserId()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Mhss History",
                testText = viewModel.state.mhssHistory,
                researches = null,
                associations = null
            ) {
                viewModel.mhssHistory()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Mhss Date History",
                testText = viewModel.state.mhssDate,
                researches = null,
                associations = null
            ) {
                viewModel.mhssSingleHistory()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Mhss Date Range History",
                testText = viewModel.state.mhssDateRange,
                researches = null,
                associations = null
            ) {
                viewModel.mhssDateRangeHistory()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Mhss Time Range History",
                testText = viewModel.state.mhssTimeRange,
                researches = null,
                associations = null
            ) {
                viewModel.mhssTimeRangeHistory()
            },
            TestButton(
                textRequired = true,
                buttonTitle = "Research Questions",
                testText = viewModel.state.researchQuestions,
                researches = null,
                associations = null
            ) {
                viewModel.researchQuestions(it)
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Participate Research",
                testText = viewModel.state.participateResearch,
                researches = null,
                associations = null
            ) {
                viewModel.participateResearch()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "All Participation",
                testText = viewModel.state.allParticipation,
                researches = null,
                associations = null
            ) {
                viewModel.getParticipation()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "All Research Participation",
                testText = viewModel.state.allResearchParticipation,
                researches = viewModel.state.researchList,
                associations = null
            ) {
                viewModel.getResearchParticipation()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "All Association Participation",
                testText = viewModel.state.allAssociationParticipation,
                researches = null,
                associations = viewModel.state.associationList
            ) {
                viewModel.getAssociationParticipation()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "All Journals",
                testText = viewModel.state.allJournals,
                researches = null,
                associations = null
            ) {
                viewModel.getAllJournals()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Single Journal",
                testText = viewModel.state.singleJournal,
                researches = null,
                associations = null
            ) {
                viewModel.getSingleJournal()
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Journal Range Participation",
                testText = viewModel.state.dateRangeJournal,
                researches = null,
                associations = null
            ) {
                viewModel.getJournalsInRange()
            },
            TestButton(
                textRequired = true,
                buttonTitle = "Journal Range Participation",
                testText = viewModel.state.dateRangeJournal,
                researches = null,
                associations = null
            ) {
                viewModel.saveJournal(it)
            },
            TestButton(
                textRequired = false,
                buttonTitle = "Association Research",
                testText = viewModel.state.associationResearch,
                researches = null,
                associations = null
            ) {
                viewModel.associationResearch()
            },
        )
        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .padding(all = 14.dp)
                .fillMaxSize()
        ) {
            list.forEach { item ->
                ButtonText(
                    textRequired = item.textRequired,
                    testResult = item.testText,
                    onClick = item.buttonClick,
                    buttonText = item.buttonTitle,
                    researches = item.researches,
                    associations = item.associations
                )
            }
        }
    }

    @Composable
    fun ButtonText(
        textRequired: Boolean,
        buttonText: String,
        onClick: (String) -> Unit,
        testResult: String,
        researches: List<ResearchParticipation>?,
        associations: List<AssociationParticipation>?
    ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        Column {
            if (textRequired)
                TextField(value = text, onValueChange = {
                    text = it
                })
            Button(onClick = {
                onClick(text.text)
            }) {
                Text(text = buttonText)
            }
            Text(text = testResult)

        }

        researches?.let {
            it.forEach { item ->
                Button(onClick = {
                    testViewModel.deleteResearch(item)
                }) {
                    Text(text = item.researchName ?: "NoName")
                }
            }
        }

        associations?.let {
            it.forEach { item ->
                Button(onClick = {
                    testViewModel.deleteAssociation(item)
                }) {
                    Text(text = item.code ?: "NoName")
                }
            }
        }
    }

    data class TestButton(
        val textRequired: Boolean,
        val testText: String,
        val buttonTitle: String,
        val researches: List<ResearchParticipation>?,
        val associations: List<AssociationParticipation>?,
        val buttonClick: (String) -> Unit,
    )
}