package com.example.mygithub.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.example.MyGitHub.databinding.ActivityLoginBinding
import com.example.mygithub.base.BaseActivity
import com.example.mygithub.cache.UserInfoCache
import com.example.mygithub.model.RepoEntity
import com.example.mygithub.view.listitem.RepoListViewBinder
import com.example.mygithub.viewmodel.LoginViewModel

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private var adapter = MultiTypeAdapter().apply {
        register(
            RepoEntity::class.java,
            RepoListViewBinder()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLoginStatus()
        initView()
        initData()
    }

    private fun initView() {
        binding.login.setOnClickListener {
            viewModel.login()
        }
        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.refreshLayout.setEnableLoadMore(false)
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getPros()
        }
        binding.rvRePos.adapter = adapter
        val layoutMgr = LinearLayoutManager(this)
        binding.rvRePos.layoutManager = layoutMgr
        binding.refreshLayout.autoRefresh()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        viewModel.loginResult.observe(this) {
            if (it) {
                checkLoginStatus()
            }
        }
        viewModel.reposListLD.observe(this) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }
        viewModel.finishRefreshOrLoadMore.observeAlive(this) {
            binding.refreshLayout.finishRefresh()
        }
        viewModel.showProgressLD.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                dismissProgressDialog()
            }
        }
    }

    private fun checkLoginStatus() {
        if (UserInfoCache.getUserInfo() != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}