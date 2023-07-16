package com.hmshohrab.commitview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hmshohrab.commitview.R
import com.hmshohrab.commitview.adapter.LoaderAdapter
import com.hmshohrab.commitview.adapter.CommitViewAdapter
import com.hmshohrab.commitview.base.BaseFragment
import com.hmshohrab.commitview.databinding.FragmentCommitBinding
import com.hmshohrab.commitview.repository.model.CommitModelItem
import com.hmshohrab.commitview.utils.SimpleCallback
import com.hmshohrab.commitview.viewmodel.CommitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CommitFragment : BaseFragment() {
    @Inject
    lateinit var commitViewAdapter: CommitViewAdapter
    private val viewModel by viewModels<CommitViewModel>()

    private lateinit var binding:FragmentCommitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommitBinding.inflate(inflater)
        //showCategoryBottomSheet()
        return binding.root
    }

    override fun configUi() {
        commitViewAdapter.clickListener = object : SimpleCallback<CommitModelItem> {
            override fun callback(any: CommitModelItem) {
                val bundle = Bundle()
                bundle.putString("username", any.committer?.login)
                findNavController().navigate(
                    R.id.action_commitFragment_to_profileFragment,
                    args = bundle
                )
            }

        }
        //   viewModel.insertAllProductVM(Utils.productItems)
        binding.productRecyclerView.adapter = commitViewAdapter.withLoadStateFooter(LoaderAdapter())
        lifecycleScope.launch {
            viewModel.commitViewData.collectLatest {
                commitViewAdapter.submitData(it)
            }
        }

    }

    override fun setupNavigation() {


    }


}