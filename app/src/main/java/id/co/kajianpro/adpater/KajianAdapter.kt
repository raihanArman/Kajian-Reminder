package id.co.kajianpro.adpater

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.kajianpro.R
import id.co.kajianpro.databinding.ItemKajianBinding
import id.co.kajianpro.home.KajianDetailActivity
import id.co.kajianpro.data.model.Kajian

class KajianAdapter(val context: Context): RecyclerView.Adapter<KajianAdapter.ViewHolder>() {

    val kajianList = mutableListOf<Kajian>()

    fun setKajianList(kajianList: List<Kajian>){
        this.kajianList.clear()
        this.kajianList.addAll(kajianList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemKajianBinding>(inflater, R.layout.item_kajian, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = kajianList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kajian = kajianList[position]
        holder.binding.kajian = kajian

        Glide.with(context)
            .load(kajian.gambar)
            .into(holder.binding.ivKajian)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, KajianDetailActivity::class.java)
            intent.putExtra("kajian", kajian)
            context.startActivity(intent)
        }

    }

    inner class ViewHolder(val binding: ItemKajianBinding): RecyclerView.ViewHolder(binding.root)

}