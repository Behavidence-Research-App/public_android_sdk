package com.behavidence.clientsdkapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClient
import com.behavidence.android.sdk_internal.domain.clients.BehavidenceClientCallback
import com.behavidence.clientsdkapp.ui.theme.CientSDKAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CientSDKAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }

        BehavidenceClient.initialize(this)

        val client = BehavidenceClient.Auth()
        client.createAnonymousProfile(object : BehavidenceClientCallback<Boolean> {
            override fun onSuccess(response: Boolean?) {
                Log.d("AnonymousResponse", "$response")
            }

            override fun onFailure(response: Boolean?, message: String?) {
                Log.d("AnonymousResponse", "$response $message")
            }

        })
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CientSDKAppTheme {
            Greeting("Android")
        }
    }
}