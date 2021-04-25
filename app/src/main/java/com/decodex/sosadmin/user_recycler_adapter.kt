package com.decodex.sosadmin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class user_recycler_adapter(private var user_list: List<user>, private var listner: users) : RecyclerView.Adapter<user_recycler_adapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): user_recycler_adapter.ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_users_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: user_recycler_adapter.ViewHolder, position: Int) {
        var currentitem = user_list[position]

        holder.name.setText("${currentitem.firstname} ${currentitem.lastname}")
        holder.age.setText(currentitem.age.toString())
        holder.phoneNo.setText(currentitem.contact)
    }

    override fun getItemCount() = user_list.size

    inner class ViewHolder(itemview:View): RecyclerView.ViewHolder(itemview), View.OnClickListener{
        val name : TextView = itemview.findViewById(R.id.Nameusers_recycler)
        val phoneNo : TextView = itemview.findViewById(R.id.phone_usersrecycler)
        val age : TextView = itemview.findViewById(R.id.ageUsersRecylcler)

        init {
            itemview.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var position = adapterPosition
            if(position!= RecyclerView.NO_POSITION){
                listner.onitemclick(position)
            }
        }


    }

    interface itemclick{
        fun onitemclick(position: Int)
    }


}