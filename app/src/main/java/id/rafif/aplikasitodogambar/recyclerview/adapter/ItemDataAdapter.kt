package id.rafif.aplikasitodogambar.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rafif.aplikasitodogambar.databinding.ItemDataBinding
import id.rafif.aplikasitodogambar.model.ModelData
import id.rafif.aplikasitodogambar.recyclerview.viewholder.ItemDataVH

/**
 * Created by Imam Fahrur Rofi on 04/09/2020.
 */
class ItemDataAdapter : RecyclerView.Adapter<ItemDataVH>() {
    private var listData = arrayListOf<ModelData>()

    fun addData(data: List<ModelData>) {
        listData.clear()
        listData.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDataVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDataBinding.inflate(inflater, parent, false)
        return ItemDataVH(binding)
    }

    override fun onBindViewHolder(holder: ItemDataVH, position: Int) {
        val data = listData[position]
        holder.bind(data)

        holder.itemView.setOnClickListener { view ->

        }
    }

    override fun getItemCount(): Int = listData.size
}