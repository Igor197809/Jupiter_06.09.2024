package com.example.jupiter_06092024

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class MainViewModel(private val context: Context) : ViewModel() {
    private val _sheetData = MutableStateFlow<List<List<Any>>>(emptyList())
    val sheetData: StateFlow<List<List<Any>>> get() = _sheetData

    init {
        loadData()
    }

    private fun getCredentials(): GoogleCredentials {
        val inputStream: InputStream = context.assets.open("jupiter_credentials.json")
        return GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf(SheetsScopes.SPREADSHEETS))
    }

    private suspend fun accessGoogleSheet(): List<List<Any>> {
        val httpTransport = NetHttpTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        val credentials = getCredentials()
        val requestInitializer = HttpCredentialsAdapter(credentials)

        val service = Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M"
        val range = "Sheet1!A1:D5"

        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        return response.getValues() ?: emptyList()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = accessGoogleSheet()
            _sheetData.value = data
        }
    }
}
