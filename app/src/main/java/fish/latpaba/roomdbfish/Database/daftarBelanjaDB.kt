package fish.latpaba.roomdbfish.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [daftarBelanja::class, HistoryBarang::class], version = 2)
abstract class daftarBelanjaDB : RoomDatabase() {

    abstract fun funDaftarBelanjaDAO(): daftarBelanjaDAO
    abstract fun historyBarangDao(): HistoryBarangDao  // Menambahkan DAO untuk historyBarang

    companion object {
        @Volatile
        private var INSTANCE: daftarBelanjaDB? = null

        fun getDatabase(context: Context): daftarBelanjaDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    daftarBelanjaDB::class.java,
                    "daftar_belanja_db"
                ).fallbackToDestructiveMigration() // Agar Room tahu jika ada perubahan skema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
