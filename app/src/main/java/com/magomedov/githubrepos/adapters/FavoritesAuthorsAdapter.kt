package com.magomedov.githubrepos.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.models.Favorites

class FavoritesAuthorsAdapter(val itemAuthorsListener: AuthorsListener) :
    RecyclerView.Adapter<FavoritesAuthorsAdapter.AuthorsViewHolder>() {

    var authors: List<Favorites> = emptyList()

    class AuthorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
        val fulName: TextView = itemView.findViewById(R.id.name)
        val electedLogin: TextView = itemView.findViewById(R.id.login)
        val delete: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.item_featured_authors, parent, false)
        return AuthorsViewHolder(itemView)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        val authors: Favorites = authors.get(position)

        Glide.with(holder.itemView)
            .load(authors.avatar)
            .into(holder.avatar)

        holder.fulName.setText(authors.nameProfile)
        holder.electedLogin.setText(authors.login)

        holder.delete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                itemAuthorsListener.onDelete(authors)
            }
        })
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    interface AuthorsListener {
        fun onDelete(authors: Favorites)

    }
}