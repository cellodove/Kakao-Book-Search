package com.cellodove.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.cellodove.book.databinding.FragmentSearchBinding
import com.cellodove.presentation.base.BaseFragment
import com.cellodove.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel : MainViewModel by activityViewModels()
    private val searchAdapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchRecycler.adapter = searchAdapter
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.searchRecycler.addItemDecoration(decoration)
        binding.btSearch.setOnClickListener {
            viewModel.searchBook(binding.etQuery.text.toString())
        }
    }


    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                searchAdapter.submitData(it)
            }
        }
    }
}