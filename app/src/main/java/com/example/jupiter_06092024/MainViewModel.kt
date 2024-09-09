import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val context: Context) : ViewModel() {

    private val _sheetData = MutableStateFlow<List<List<Any>>>(emptyList())
    val sheetData: StateFlow<List<List<Any>>> = _sheetData
    private val TAG = "MainViewModel"

    init {
        Log.d(TAG, "ViewModel инициализирован")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Начало загрузки данных")
                val data = accessGoogleSheet()
                _sheetData.value = data
                Log.d(TAG, "Данные успешно загружены: $data")
            } catch (e: GoogleJsonResponseException) {
                Log.e(TAG, "Ошибка при загрузке данных: ${e.statusCode} - ${e.message}", e)
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при загрузке данных: ${e.message}", e)
            }
        }
    }

    private suspend fun accessGoogleSheet(): List<List<Any>> {
        val httpTransport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        Log.d(TAG, "Запрос данных с Google Sheets")
        val credentials = getCredentials() // Функция для получения учетных данных OAuth
        val sheetsService = Sheets.Builder(httpTransport, jsonFactory, credentials)
            .setApplicationName("Jupiter App")
            .build()

        val spreadsheetId = "ваш_spreadsheetId"
        val range = "Sheet1!A1:D5"

        val response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute()
        Log.d(TAG, "Ответ от Google Sheets: $response")

        return response.getValues() ?: emptyList()
    }

    private fun getCredentials(): Sheets {
        // Здесь ваш код для получения учетных данных OAuth
    }
}
