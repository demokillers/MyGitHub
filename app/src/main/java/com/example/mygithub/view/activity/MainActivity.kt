package com.example.mygithub.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.example.MyGitHub.databinding.ActivityMainBinding
import com.example.mygithub.base.BaseActivity
import com.example.mygithub.cache.UserInfoCache
import com.example.mygithub.viewmodel.MainViewModel
import com.example.mygithub.view.listitem.RepoListViewBinder
import com.example.mygithub.model.RepoEntity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private val userInfo by lazy {
        UserInfoCache.getUserInfo()
    }
    private var adapter = MultiTypeAdapter().apply {
        register(
            RepoEntity::class.java,
            RepoListViewBinder()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        binding.avatar.setImageURI(userInfo?.avatar_url)
        binding.refreshLayout.setEnableLoadMore(false)
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getPros(userInfo?.login.orEmpty())
        }
        binding.logout.setOnClickListener {
            UserInfoCache.clearUserInfo()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.rvRePos.adapter = adapter
        val layoutMgr = LinearLayoutManager(this)
        binding.rvRePos.layoutManager = layoutMgr
        binding.refreshLayout.autoRefresh()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        viewModel.reposListLD.observe(this) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }
        viewModel.finishRefreshOrLoadMore.observeAlive(this) {
            binding.refreshLayout.finishRefresh()
        }
    }
}