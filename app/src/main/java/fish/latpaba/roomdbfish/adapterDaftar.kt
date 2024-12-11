package fish.latpaba.roomdbfish

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fish.latpaba.roomdbfish.Database.daftarBelanja
import fish.latpaba.roomdbfish.Database.daftarBelanjaDB

//Buatlah sebuah Class baru yang akan kita gunakan sebagai adapter untuk menghubungkan layout dan juga dataset
//yang sudah kita siapkan. Beri nama adapterRevView dengan menambahkan parameter listWayang dan extend ke
//adapterRevView.Adapter. Dengan merubah class adapterRevView menjadi seperti berikut.
//Apabila masih ada yang diberi garis merah / error seperti di gambar atas, biarkan saja, karena kita akan melengkapi
//pada langkah berikutnya.
class adapterDaftar (private val daftarBelanja : MutableList<daftarBelanja>) : RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback{
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list, parent, false
        )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    fun isiData(daftar: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]

        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)


        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener{
            onItemClickCallback.delData(daftar)
        }
    }


    class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.namaItem)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.jumlahItem)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tanggalItem)

        var _btnEdit = itemView.findViewById<ImageButton>(R.id.btnEditItem)
        var _btnDelete = itemView.findViewById<ImageButton>(R.id.btnDeleteItem)
    }


}