package com.example.mygithub.view.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import com.example.MyGitHub.R
import com.example.MyGitHub.databinding.LayoutRepoListBinding
import com.example.mygithub.MyApplication
import com.example.mygithub.model.RepoEntity
import com.example.mygithub.utils.BindingViewHolder
import com.example.mygithub.view.activity.RepoContentActivity

class RepoListViewBinder :
    ItemViewBinder<RepoEntity, BindingViewHolder<LayoutRepoListBinding>>() {
    override fun onBindViewHolder(
        holder: BindingViewHolder<LayoutRepoListBinding>,
        item: RepoEntity
    ) {
        holder.binding.name.text =
            MyApplication.instance.getString(R.string.repositories, item.name)
        holder.binding.root.setOnClickListener {
            RepoContentActivity.startActivity(holder.itemView.context, item)
        }
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BindingViewHolder<LayoutRepoListBinding> {
        return BindingViewHolder(LayoutRepoListBinding.inflate(inflater, parent, false))
    }
}