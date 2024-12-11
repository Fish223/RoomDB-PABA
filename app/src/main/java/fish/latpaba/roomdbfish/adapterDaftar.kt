package fish.latpaba.roomdbfish

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import fish.latpaba.roomdbfish.Database.daftarBelanja
import fish.latpaba.roomdbfish.Database.HistoryBarang

class adapterDaftar(
    private val daftar: MutableList<Any>,  // Menggunakan Any untuk mendukung berbagai tipe data
    private val onSelesaiClick: (Any) -> Unit  // Fungsi selesai yang menangani kedua tipe data
) : RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = daftar.size

    // Fungsi untuk memperbarui data
    fun isiData(data: List<Any>) {
        daftar.clear()
        daftar.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = daftar[position]
        when (item) {
            is daftarBelanja -> {
                holder._tvTanggal.text = item.tanggal
                holder._tvItemBarang.text = item.item
                holder._tvJumlahBarang.text = item.jumlah.toString()
            }
            is HistoryBarang -> {
                holder._tvTanggal.text = item.tanggalSelesai
                holder._tvItemBarang.text = item.item
                holder._tvJumlahBarang.text = item.jumlah.toString()
            }
        }

        holder._btnSelesai.setOnClickListener {
            onSelesaiClick(item)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang: TextView = itemView.findViewById(R.id.namaItem)
        var _tvJumlahBarang: TextView = itemView.findViewById(R.id.jumlahItem)
        var _tvTanggal: TextView = itemView.findViewById(R.id.tanggalItem)
        var _btnSelesai: Button = itemView.findViewById(R.id.btnSelesai)
    }
}
