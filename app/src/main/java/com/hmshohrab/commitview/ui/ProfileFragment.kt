package com.hmshohrab.commitview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.hmshohrab.commitview.adapter.CommitViewAdapter
import com.hmshohrab.commitview.base.BaseFragment
import com.hmshohrab.commitview.base.Status
import com.hmshohrab.commitview.databinding.FragmentProfileBinding
import com.hmshohrab.commitview.utils.toast
import com.hmshohrab.commitview.viewmodel.CommitViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var commitViewAdapter: CommitViewAdapter
    private val viewModel by viewModels<CommitViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        //showCategoryBottomSheet()
        return binding.root
    }


    override fun configUi() {
        val userName = arguments?.getString("username", "hmshohrab")

        viewModel.getUserProfile(userName ?: "hmshohrab")

        viewModel.userModelResult.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { userModel ->
                        binding.bioTextView.text = userModel.name
                        binding.usernameTextView.text = userModel.login
                        binding.profileImageView.load(userModel.avatar_url)
                        binding.bioTextView.text = userModel.bio
                        binding.publicReposTextview.text = "Public Repos: ${userModel.public_repos}"
                        binding.publicGistsTextview.text = "Public Gists: ${userModel.public_gists}"
                    }
                }

                Status.LOADING -> {}
                Status.ERROR -> {
                    val errMsg = it.error?.message ?: ""
                    toast(errMsg)
                }
            }

        }
    }


}
