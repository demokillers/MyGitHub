package com.example.mygithub.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.example.MyGitHub.databinding.ActivitySearchBinding
import com.example.mygithub.base.BaseActivity
import com.example.mygithub.model.RepoEntity
import com.example.mygithub.view.listitem.RepoListViewBinder
import com.example.mygithub.viewmodel.SearchViewModel

class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }
    private var adapter = MultiTypeAdapter().apply {
        register(
            RepoEntity::class.java,
            RepoListViewBinder()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        binding.back.setOnClickListener {
            finish()
        }
        binding.search.setOnClickListener {
            viewModel.searchPos(binding.searchET.text.toString(), binding.filterPython.isSelected)
        }
        binding.filterPython.setOnClickListener {
            binding.filterPython.isSelected = !binding.filterPython.isSelected
        }
        binding.recyclerView.adapter = adapter
        val layoutMgr = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutMgr
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        viewModel.reposListLD.observe(this) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }
        viewModel.showProgressLD.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                dismissProgressDialog()
            }
        }
        viewModel.toastPD.observeAlive(this) {
            Toast.makeText(
                this, it, Toast.LENGTH_SHORT
            ).show()
        }
    }
}