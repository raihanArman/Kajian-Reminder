package id.co.kajianpro.adpater

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.kajianpro.BuildConfig
import id.co.kajianpro.R
import id.co.kajianpro.databinding.ItemSemuaKajianBinding
import id.co.kajianpro.data.model.Kajian
import id.co.kajianpro.home.KajianDetailActivity

class KajianAllAdapter(val context: Context): RecyclerView.Adapter<KajianAllAdapter.ViewHolder>() {

    val kajianList = mutableListOf<Kajian>()

    fun setKajianList(kajianList: List<Kajian>){
        this.kajianList.clear()
        this.kajianList.addAll(kajianList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemSemuaKajianBinding>(inflater, R.layout.item_semua_kajian, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = kajianList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kajian = kajianList[position]
        holder.binding.kajian = kajian

        val tanggal = DateFormat.format("dd MMM yyyy", kajian.tanggal).toString()
        val jam = DateFormat.format("HH:mm", kajian.tanggal).toString()

        holder.binding.jam = jam
        holder.binding.tanggal = tanggal

        Glide.with(context)
            .load(BuildConfig.BASE_URL_GAMBAR+kajian.gambar)
            .into(holder.binding.ivKajian)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, KajianDetailActivity::class.java)
            intent.putExtra("kajian", kajian)
            context.startActivity(intent)
        }

    }

    inner class ViewHolder(val binding: ItemSemuaKajianBinding
    ): RecyclerView.ViewHolder(binding.root)

}