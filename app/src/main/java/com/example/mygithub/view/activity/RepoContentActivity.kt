package com.example.mygithub.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.MyGitHub.R
import com.example.MyGitHub.databinding.ActivityRepoDetailBinding
import com.example.mygithub.base.BaseActivity
import com.example.mygithub.cache.UserInfoCache
import com.example.mygithub.model.RepoEntity
import com.example.mygithub.viewmodel.RepoContentViewModel

class RepoContentActivity : BaseActivity() {

    companion object {
        private const val KEY_DATA = "KEY_DATA"
        fun startActivity(context: Context, data: RepoEntity) {
            val intent = Intent(context, RepoContentActivity::class.java)
            intent.putExtra(KEY_DATA, data)
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[RepoContentViewModel::class.java]
    }
    private val userInfo by lazy {
        UserInfoCache.getUserInfo()
    }
    private lateinit var binding: ActivityRepoDetailBinding
    private var data: RepoEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.getParcelableExtra<RepoEntity>(KEY_DATA)
        initView()
        initData()
    }

    private fun initView() {
        binding.addIssue.setOnClickListener {
            viewModel.addIssues(
                data, binding.issueTitle.text.toString(), binding.issueBody.text.toString()
            )
        }
        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        viewModel.getPos(data)
        viewModel.reposLD.observe(this) {
            binding.issueLayout.isVisible = userInfo?.login == data?.owner?.login
            binding.ownerName.text = getString(R.string.ownerName, it.owner.login)
            binding.repositories.text = getString(R.string.repositories, it.name)
            binding.forkNum.text = getString(R.string.forkNum, it.forks_count)
            binding.starNum.text = getString(R.string.starNum, it.stargazers_count)
            binding.language.text = getString(R.string.language, it.language)
        }
        viewModel.toastPD.observeAlive(this) {
            Toast.makeText(
                this, it, Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.showProgressLD.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                dismissProgressDialog()
            }
        }
    }
}