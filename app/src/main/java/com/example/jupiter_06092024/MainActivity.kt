package com.example.jupiter_06092024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jupiter_06092024.ui.theme.Jupiter_06092024Theme
import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.google.auth.http.HttpCredentialsAdapter


fun getCredentials(context: Context, jsonKeyPath: String): GoogleCredentials {
    val inputStream = context.assets.open(jsonKeyPath)
    return GoogleCredentials.fromStream(inputStream)
        .createScoped(listOf(SheetsScopes.SPREADSHEETS))
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Работа с Google Sheets API
        CoroutineScope(Dispatchers.IO).launch {
            accessGoogleSheet()
        }

        setContent {
            Jupiter_06092024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private suspend fun accessGoogleSheet() {
        val httpTransport = NetHttpTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        val credentials = getCredentials(this, "jupiter_credentials.json")
        val requestInitializer = HttpCredentialsAdapter(credentials)

        val service = Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"
        val range = "Sheet1!A1:D5"

        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        val values = response.getValues()

        withContext(Dispatchers.Main) {
            if (values != null && values.isNotEmpty()) {
                for (row in values) {
                    // Вывод строки в лог
                    println(row)
                }
            } else {
                println("Данные не найдены.")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Jupiter_06092024Theme {
        Greeting("Android")
    }
}
