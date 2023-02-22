package com.magomedov.githubrepos.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.ItemFeaturedAuthorsBinding
import com.magomedov.githubrepos.models.Favorites

class FavoritesAuthorsAdapter(val itemAuthorsListener: AuthorsListener) :
    RecyclerView.Adapter<FavoritesAuthorsAdapter.AuthorsViewHolder>() {

    var authors: List<Favorites> = emptyList()

    class AuthorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val binding : ItemFeaturedAuthorsBinding = ItemFeaturedAuthorsBinding.bind(itemView)

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
            .into(holder.binding.avatar)

        holder.binding.name.setText(authors.nameProfile)
        holder.binding.login.setText(authors.login)

        holder.binding.delete.setOnClickListener(object : View.OnClickListener {
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