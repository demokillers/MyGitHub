package com.example.mygithub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.MyGitHub.databinding.LayoutProgressDialogBinding

class ProgressDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "progressDialogFragment"
        fun newInstance() = ProgressDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不保留活动下，重建不显示
        if (savedInstanceState != null) {
            dismissAllowingStateLoss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return LayoutProgressDialogBinding.inflate(inflater, container, false).root
    }

    fun show(
        fragmentManager: FragmentManager, isCancelable: Boolean = false
    ) {
        this.isCancelable = isCancelable
        try {
            show(fragmentManager, TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}