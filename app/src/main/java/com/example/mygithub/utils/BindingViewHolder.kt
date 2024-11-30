package com.example.mygithub.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BindingViewHolder<T : ViewBinding>(val binding: T) :
    RecyclerView.ViewHolder(binding.root)