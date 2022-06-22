package com.cellodove.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.cellodove.book.R
import com.cellodove.book.databinding.FragmentSearchBinding
import com.cellodove.domain.model.response.Documents
import com.cellodove.presentation.base.BaseFragment
import com.cellodove.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel : MainViewModel by activityViewModels()
    private val searchAdapter = SearchAdapter()
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchRecycler.adapter = searchAdapter
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.searchRecycler.addItemDecoration(decoration)
        binding.btSearch.setOnClickListener {
            searchBook(binding.etQuery.text.toString())
            searchAdapter.setSearchWord(binding.etQuery.text.toString())
        }
        searchAdapter.setItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onClick(documents: Documents,isLike : Boolean) {
                val bundle = Bundle()
                bundle.putBoolean("isLike",isLike)
                bundle.putString("title", documents.title)
                bundle.putString("thumbnail", documents.thumbnail)
                bundle.putString("price", documents.price.toString())
                bundle.putString("publisher", documents.publisher)
                bundle.putString("contents", documents.contents)
                findNavController().navigate(R.id.BookDetailsFragment,bundle)
            }
        })
    }

    override fun observeViewModel() {
        refreshList()
    }

    private fun searchBook(query :String){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchBook(query).collectLatest {
                searchAdapter.submitData(it)
            }
        }
    }

    private fun refreshList() {
        lifecycleScope.launch {
            searchAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.searchRecycler.scrollToPosition(0) }
        }
    }
}