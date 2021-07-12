package id.co.kajianpro.adpater

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.kajianpro.BuildConfig
import id.co.kajianpro.OnClickReminderListener
import id.co.kajianpro.R
import id.co.kajianpro.databinding.ItemReminderBinding
import id.co.kajianpro.data.model.Kajian

class ReminderAdapter(val context: Context, val onClickReminderListener: OnClickReminderListener): RecyclerView.Adapter<ReminderAdapter.ViewHolder>(){

    val listReminder = mutableListOf<Kajian>()

    fun setListReminder(listKajian: List<Kajian>){
        this.listReminder.clear()
        this.listReminder.addAll(listKajian)
        notifyDataSetChanged()
    }

    inner class ViewHolder (val binding: ItemReminderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemReminderBinding>(inflater, R.layout.item_reminder, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listReminder.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kajian = listReminder[position]
        holder.binding.kajian = kajian


        val tanggal = DateFormat.format("dd MMM yyyy", kajian.tanggal).toString()
        val jam = DateFormat.format("HH:mm", kajian.tanggal).toString()

        holder.binding.jam = jam
        holder.binding.tanggal = tanggal

        Glide.with(context)
            .load(BuildConfig.BASE_URL_GAMBAR+kajian.gambar)
            .into(holder.binding.ivKajian)

        holder.binding.btIniciarSesion.setOnClickListener {
            onClickReminderListener.onClickReminder(kajian)
        }

    }

}