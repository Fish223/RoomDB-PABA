package fish.latpaba.roomdbfish

import android.os.Bundle
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
    private lateinit var historyAdapter: adapterDaftar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        db = daftarBelanjaDB.getDatabase(this)
        historyAdapter = adapterDaftar(mutableListOf(), { item ->

            markAsCompleted(item)
        })

        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = historyAdapter

        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val historyList = db.historyBarangDao().getAll()
            withContext(Dispatchers.Main) {
                historyAdapter.isiData(historyList)
            }
        }
    }

    private fun markAsCompleted(item: Any) {

    }
}
