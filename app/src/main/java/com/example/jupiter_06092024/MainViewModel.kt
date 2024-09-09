package com.example.jupiter_06092024

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class MainViewModel(private val context: Context) : ViewModel() {

    var sheetData = mutableStateOf<List<List<Any>>>(emptyList())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = accessGoogleSheet()
            sheetData.value = data
        }
    }

    private suspend fun accessGoogleSheet(): List<List<Any>> {
        val httpTransport = com.google.api.client.http.javanet.NetHttpTransport()
        val jsonFactory = com.google.api.client.json.jackson2.JacksonFactory.getDefaultInstance()

        // Получаем ключи доступа из файла
        val credentials = getCredentials(context)
        val requestInitializer = HttpCredentialsAdapter(credentials)

        val service = Sheets.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "1J5wxqk1_nCPEUBnilSHI8RgnWU7CUV-vxrAVGl6LX5M" // Ваш ID таблицы Google
        val range = "Sheet1!A1:D5" // Диапазон данных

        val response = service.spreadsheets().values().get(spreadsheetId, range).execute()
        return response.getValues() ?: emptyList()
    }

    private fun getCredentials(context: Context): GoogleCredentials {
        val inputStream: InputStream = context.assets.open("jupiter_credentials.json") // Файл с ключами
        return GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf(SheetsScopes.SPREADSHEETS_READONLY))
    }
}
