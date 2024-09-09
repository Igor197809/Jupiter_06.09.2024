package com.example.jupiter_06092024

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.InputStream
import com.google.api.client.http.javanet.NetHttpTransport

class MainViewModel(private val context: Context) : ViewModel() {

    private val _sheetData = MutableStateFlow<List<List<Any>>>(emptyList())
    val sheetData: StateFlow<List<List<Any>>> = _sheetData
    private val TAG = "MainViewModel"

    init {
        Log.d(TAG, "ViewModel initialized")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Loading data from Google Sheets")
                val data = accessGoogleSheet()
                _sheetData.value = data
                Log.d(TAG, "Data successfully loaded: $data")
            } catch (e: GoogleJsonResponseException) {
                Log.e(TAG, "Error loading data: ${e.statusCode} - ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Error loading data: ${e.message}", e)
            }
        }
    }

    private suspend fun accessGoogleSheet(): List<List<Any>> {
        Log.d(TAG, "Начало вызова Google Sheets API")
        val httpTransport = NetHttpTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        val credentials = getCredentials(context, "jupiter_credentials.json")
        val requestInitializer = HttpCredentialsAdapter(credentials)

        val service = Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"
        val range = "Sheet1!A1:D5"

        Log.d(TAG, "Запрос в Google Sheets")
        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        Log.d(TAG, "Ответ от Google Sheets получен")

        return response.getValues() ?: emptyList()
    }

    private fun getCredentials(context: Context, credentialsFileName: String): GoogleCredentials {
        val inputStream: InputStream = context.assets.open(credentialsFileName)
        return GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))
    }
}
