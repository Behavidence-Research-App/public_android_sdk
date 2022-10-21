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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.behavidence.android.sdk_internal.data.repository.Auth.AuthClient
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

        AuthClient(this)
    }
}

@Composable
fun TestSDK(
    viewModel: TestViewModel
) {
    val scroll = rememberScrollState()
    val label = remember {
        mutableStateOf("")
    }
    val list = listOf(
        TestButton(
            buttonTitle = "AuthClick",
            testText = viewModel.state.authInfo
        ) {
            Log.i("check","auth button click")
            label.value = "AuthClicked"
            viewModel.getAuth()
        },
        TestButton(
            buttonTitle = "AuthRefresh",
            testText = viewModel.state.authRefresh,
        ) {
            viewModel.authRefresh()
        },
        TestButton(
            buttonTitle = "Mhss",
            testText = viewModel.state.mhss,
        ) {
            viewModel.mhss()
        },TestButton(
            buttonTitle = "Mhss History",
            testText = viewModel.state.mhssHistory,
        ) {
            viewModel.mhssHistory()
        },TestButton(
            buttonTitle = "Association Research",
            testText = viewModel.state.associationResearch,
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
                testResult = item.testText,
                onClick = item.buttonClick,
                buttonText = item.buttonTitle
            )
        }
    }
}

@Composable
fun ButtonText(
    buttonText: String,
    onClick:() -> Unit,
    testResult: String
) {
    Column {
        Button(onClick = {
            onClick()
        }) {
            Text(text = buttonText)
        }
        Text(text = testResult)
    }
}

data class TestButton(
    val testText: String,
    val buttonTitle: String,
    val buttonClick: () -> Unit,
)