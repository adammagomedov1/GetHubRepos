package com.magomedov.githubrepos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magomedov.githubrepos.R
import com.magomedov.githubrepos.databinding.ItemRepositoryBinding
import com.magomedov.githubrepos.models.Repository

class RepositoryAdapter(val repositoryListener: RepositoryAdapter.RepositoryListener) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositoryList: List<Repository> = emptyList()

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemRepositoryBinding = ItemRepositoryBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_repository,
            parent,
            false
        )

        return RepositoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository: Repository = repositoryList.get(position)
        holder.binding.name.setText(repository.name)
        holder.binding.description.setText(repository.description)
        holder.binding.owner.setText(repository.owner.login)


        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                repositoryListener.onItemClick(repository)

            }
        })
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }


    interface RepositoryListener {
        fun onItemClick(repository: Repository)
    }
}
