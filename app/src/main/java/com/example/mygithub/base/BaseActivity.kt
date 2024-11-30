package com.example.mygithub.base

import androidx.appcompat.app.AppCompatActivity
import com.example.mygithub.view.fragment.ProgressDialogFragment

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialogFragment: ProgressDialogFragment

    /**
     * 显示加载(转圈)对话框
     */
    fun showProgressDialog() {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, false)
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun dismissProgressDialog() {
        (supportFragmentManager.findFragmentByTag(ProgressDialogFragment.TAG) as? ProgressDialogFragment)?.dismissAllowingStateLoss()
    }
}