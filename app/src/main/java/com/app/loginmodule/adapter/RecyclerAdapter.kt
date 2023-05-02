package com.app.loginmodule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.loginmodule.R
import com.app.loginmodule.dataclass.User

class RecyclerAdapter(
    private val activity: FragmentActivity,
    val listResponse: List<User>?
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list, parent, false)
        return RecyclerAdapter.ViewHolder(view)
    }


    override fun getItemCount(): Int {

        return listResponse!!.size


    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvemail.text = "Email: "+ listResponse!!.get(position).email
        holder.tvname.text = "Name: "+ listResponse!!.get(position).name



    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvname = itemView.findViewById<TextView>(R.id.tvname)
        val tvemail = itemView.findViewById<TextView>(R.id.tvemail)


    }
}