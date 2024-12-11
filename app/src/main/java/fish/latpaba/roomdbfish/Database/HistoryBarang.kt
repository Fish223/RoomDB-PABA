package fish.latpaba.roomdbfish.Database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history_barang")
data class HistoryBarang(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val item: String,
    val jumlah: Int,
    val tanggalSelesai: String
)
