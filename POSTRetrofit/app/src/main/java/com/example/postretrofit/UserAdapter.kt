package com.example.postretrofit


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_row.view.*

class UserAdapter( private val myUsers: List<Users.UserDetails>):  RecyclerView.Adapter<UserAdapter.ItemViewHolder>(){
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val list1 =myUsers[position]

        holder.itemView.apply {
            tvname.text = list1.name
            tvlocation.text = list1.location
            tvPk.text= list1.pk.toString()


        }
    }

    override fun getItemCount() = myUsers.size
}