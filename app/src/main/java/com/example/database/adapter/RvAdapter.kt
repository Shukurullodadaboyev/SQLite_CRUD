package com.example.database.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.database.databinding.ItemrvBinding
import com.example.database.models.User

class RvAdapter(var list: ArrayList<User>, var rvAction: RvAction) : RecyclerView.Adapter<RvAdapter.VH>() {
    inner class VH(val itemRvBinding: ItemrvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: User) {
            itemRvBinding.text1.text = user.name
            itemRvBinding.text2.text = user.number
            itemRvBinding.btnMore.setOnClickListener {
                rvAction.moreClick(user, itemRvBinding.btnMore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemrvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    interface RvAction {
        fun itemClick(user: User)
        fun moreClick(user: User, imageView: ImageView)
    }
}