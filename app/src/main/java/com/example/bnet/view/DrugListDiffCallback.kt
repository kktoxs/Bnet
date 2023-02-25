package com.example.bnet.view

import androidx.recyclerview.widget.DiffUtil
import com.example.bnet.data.LightDrug

class DrugListDiffCallback : DiffUtil.ItemCallback<LightDrug>() {
    override fun areItemsTheSame(oldItem: LightDrug, newItem: LightDrug): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LightDrug, newItem: LightDrug): Boolean {
        return oldItem == newItem
    }

}
