package com.example.jupiter_06092024

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule


class MainViewModel(private val context: Context) : ViewModel() {
    var sheetData = MutableStateFlow<List<List<Any>>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            sheetData.value = accessGoogleSheet()
        }
    }

    private fun getCredentials(): GoogleCredentials {
        val inputStream = context.assets.open("jupiter_credentials.json")
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
}
