package fish.latpaba.roomdbfish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fish.latpaba.roomdbfish.Database.HistoryBarang

class HistoryAdapter(
    private val historyList: MutableList<HistoryBarang>
) : RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = historyList[position]
        holder._tvTanggal.text = history.tanggalSelesai
        holder._tvItemBarang.text = history.item
        holder._tvJumlahBarang.text = history.jumlah.toString()
    }

    override fun getItemCount(): Int = historyList.size

    fun isiData(daftar: List<HistoryBarang>) {
        historyList.clear()
        historyList.addAll(daftar)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang: TextView = itemView.findViewById(R.id.namaItem)
        var _tvJumlahBarang: TextView = itemView.findViewById(R.id.jumlahItem)
        var _tvTanggal: TextView = itemView.findViewById(R.id.tanggalItem)
    }
}
