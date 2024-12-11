package fish.latpaba.roomdbfish.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryBarangDao {
    @Insert
    suspend fun insert(historyBarang: HistoryBarang)

    @Query("SELECT * FROM history_barang ORDER BY tanggalSelesai DESC")
    suspend fun getAll(): List<HistoryBarang>
}
