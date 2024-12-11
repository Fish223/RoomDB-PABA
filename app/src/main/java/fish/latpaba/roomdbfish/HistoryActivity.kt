package fish.latpaba.roomdbfish

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fish.latpaba.roomdbfish.Database.daftarBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private lateinit var db: daftarBelanjaDB
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Inisialisasi database dan adapter baru
        db = daftarBelanjaDB.getDatabase(this)
        historyAdapter = HistoryAdapter(mutableListOf())

        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = historyAdapter

        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {

            val historyList = db.historyBarangDao().getAll()
            withContext(Dispatchers.Main) {
                if (historyList.isNotEmpty()) {
                    historyAdapter.isiData(historyList)
                } else {

                    Log.d("HistoryActivity", "No data found")
                }
            }
        }
    }
}
