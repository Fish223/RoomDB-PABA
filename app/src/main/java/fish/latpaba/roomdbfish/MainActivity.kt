package fish.latpaba.roomdbfish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fish.latpaba.roomdbfish.Database.HistoryBarang
import fish.latpaba.roomdbfish.Database.daftarBelanja
import fish.latpaba.roomdbfish.Database.daftarBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var adapterDaftar: adapterDaftar
    private lateinit var DB: daftarBelanjaDB
    private var arDaftar: MutableList<Any> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DB = daftarBelanjaDB.getDatabase(this)
        adapterDaftar = adapterDaftar(arDaftar) { item ->
            markAsCompleted(item)
        }

        var _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }

        var _rvDaftar = findViewById<RecyclerView>(R.id.rvNotes)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar




        val fabHistory = findViewById<FloatingActionButton>(R.id.fabHistory)
        fabHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        loadData()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val daftarBelanja = DB.funDaftarBelanjaDAO().selectAll()
            withContext(Dispatchers.Main) {
                adapterDaftar.isiData(daftarBelanja)
            }
        }
    }

    private fun markAsCompleted(item: Any) {
        CoroutineScope(Dispatchers.IO).launch {
            when (item) {
                is daftarBelanja -> {
                    // Hapus dari daftarBarang_DB
                    DB.funDaftarBelanjaDAO().delete(item)

                    // Tambahkan ke historyBarang_DB
                    val historyItem = HistoryBarang(
                        item = item.item ?: "Unknown",
                        jumlah = item.jumlah?.toIntOrNull() ?: 0,
                        tanggalSelesai = item.tanggal ?: "Unknown"
                    )
                    DB.historyBarangDao().insert(historyItem)
                }
            }

            // Muat ulang data RecyclerView
            withContext(Dispatchers.Main) {
                loadData()
            }
        }
    }
}


