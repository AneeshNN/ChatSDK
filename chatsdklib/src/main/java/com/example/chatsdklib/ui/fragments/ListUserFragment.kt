package com.example.chatsdklib.ui.fragments

/**
 * Created by Aneesh NN on 9/17/20.
 */

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatsdklib.R
import com.example.chatsdklib.adapters.SampleAdapter
import com.example.chatsdklib.base.ui.BaseFragment
import com.example.chatsdklib.databinding.ActivityMainBinding
import com.example.chatsdklib.interfaces.CallbackOnClick
import com.example.chatsdklib.interfaces.SDKCallback
import com.example.chatsdklib.ui.view_model.ListUserViewModel
import com.example.chatsdklib.util.ClickUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.NotNull
import java.util.*


class ListUserFragment : BaseFragment() {
    private lateinit var viewModel: ListUserViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var sdkCallback: SDKCallback
    private var isHide = false
    companion object {
        val KEY_HIDE_TITLE = "hive_title"
        private var callback: SDKCallback? = null

        fun newInstance(
            hideTitle: Boolean,
            callback: SDKCallback
        ): ListUserFragment {

            val listUserFragment = ListUserFragment()
            listUserFragment.sdkCallback = callback
            val mBundle = Bundle()
            mBundle.putBoolean(KEY_HIDE_TITLE, hideTitle)
            listUserFragment.arguments = mBundle
            return listUserFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        viewModel = activity?.run {
            ViewModelProvider(this)[ListUserViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this
        isHide = arguments?.getBoolean(KEY_HIDE_TITLE, false)!!

        return binding.root
    }


    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alUsers = ArrayList<String>()
        alUsers.add("User - 1")
        alUsers.add("User - 2")
        alUsers.add("User - 3")
        alUsers.add("User - 4")
        alUsers.add("User - 5")

        setAdapterAndLayoutManager(alUsers)

        hideTitle(isHide)
    }


    fun hideTitle(isHide: Boolean) {
        when(isHide){
            true -> text_view_user_name.visibility = View.GONE
            false -> text_view_user_name.visibility = View.VISIBLE
        }
    }
    /**
     * Sets user data
     */
    private fun setAdapterAndLayoutManager(@NotNull items: ArrayList<String>) {
        recycler_view.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recycler_view.adapter =
            SampleAdapter(requireContext(),
                items,
                object : CallbackOnClick {
                    override fun onClick(position: Int, data: Any) {
                        if (ClickUtils.instance.check(ClickUtils.BUTTON_CLICK)) {
                            var userData: String = data as String
                            showToast("Toast from AAR lib - $userData")
                            sdkCallback.onUserDataLoaded(userData)
                        }
                    }
                })
    }
}