package com.example.bnet.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bnet.R
import com.example.bnet.data.FullDrug
import com.example.bnet.data.LightDrug

class DrugListAdapter(private val context: Context, private val callback: (Int)->Unit) :
    ListAdapter<LightDrug, DrugListAdapter.DrugViewHolder>(DrugListDiffCallback()) {

    var onDrugClickListener: ((LightDrug) -> Unit)? = null

    class DrugViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.drug_image)
        val title: TextView = view.findViewById(R.id.drug_title_tv)
        val description: TextView = view.findViewById(R.id.drug_description_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent,
            false
        )
        return DrugViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrugViewHolder, position: Int) {
        val drug = getItem(position)
        Log.d("adapter","onBindViewHolder position $position")
        Log.d("adapter","onBindViewHolder itemCount $itemCount")
        Log.d("adapter","onBindViewHolder drug?.name ${drug.name}")
        holder.title.text = drug?.name
        holder.description.text = drug?.description
        if(itemCount - position == 1){
            callback(itemCount)
        }

        holder.view.setOnClickListener {
            if (drug != null) {
                onDrugClickListener?.invoke(drug)
            }
        }

        Glide
            .with(context)
            .load("http://shans.d2.i-partner.ru" + drug.image)
            .into(holder.image)
    }

    fun updateList(newData: List<LightDrug>?){
        if(newData.isNullOrEmpty()){
            Log.d("adapter", "empty list")
        }else {
            val newList = currentList + newData
            submitList(newList)
        }
        Log.d("adapter data", "LIST UPDATED: new data - $newData")
    }

}