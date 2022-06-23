package com.cellodove.presentation.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.cellodove.book.R
import com.cellodove.book.databinding.FragmentSearchBinding
import com.cellodove.domain.model.response.Documents
import com.cellodove.presentation.base.BaseFragment
import com.cellodove.presentation.ui.main.MainViewModel
import com.cellodove.presentation.util.decideOnState
import com.cellodove.presentation.util.initToolbar
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
        initToolbar(binding.layoutToolbar,"Pay", leftIcon = R.drawable.ic_baseline_favorite_24)
        viewInit()
    }

    override fun onResume() {
        super.onResume()
        searchAdapter.setCheckLike(viewModel.getCheckLikeList())
        searchAdapter.refresh()
    }

    override fun observeViewModel() {
        refreshList()
    }

    private fun viewInit(){
        binding.searchRecycler.adapter = searchAdapter
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.searchRecycler.addItemDecoration(decoration)

        binding.etQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.deleteAllLikeList()
                searchBook(binding.etQuery.text.toString())
                searchAdapter.setSearchWord(binding.etQuery.text.toString())
                true
            } else {
                false
            }
        }

        searchAdapter.setItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onClick(documents: Documents) {
                val bundle = Bundle()
                bundle.putString("title", documents.title)
                bundle.putString("thumbnail", documents.thumbnail)
                bundle.putInt("price", documents.price)
                bundle.putString("publisher", documents.publisher)
                bundle.putString("contents", documents.contents)
                findNavController().navigate(R.id.BookDetailsFragment,bundle)
            }
        })

        searchAdapter.setLikeButtonClickListener(object  : SearchAdapter.OnLikeButtonClickListener{
            override fun onClick(isLike: Boolean,title : String) {
                if (isLike){
                    viewModel.addCheckLike(title)
                }else{
                    viewModel.deleteCheckLike(title)
                }
            }
        })

        binding.retryButton.setOnClickListener {
            searchAdapter.retry()
        }
    }

    private fun searchBook(query :String){
        searchJob = lifecycleScope.launch {
            viewModel.searchBook(query).collectLatest {
                binding.searchNothing.isVisible = false
                binding.errorLayout.isVisible = false
                binding.searchRecycler.visibility = View.VISIBLE
                searchAdapter.setCheckLike(viewModel.getCheckLikeList())
                searchAdapter.submitData(it)
            }
        }
    }

    private fun refreshList() {
        searchAdapter.addLoadStateListener { loadState ->
            loadState.decideOnState(
                adapter = searchAdapter,
                showLoading = { visible ->
                    binding.progressBar.isVisible = visible
                    binding.searchNothing.isVisible = false
                    binding.errorLayout.isVisible = false
                },
                showEmptyState = { visible ->
                    binding.searchRecycler.scrollToPosition(0)
                    binding.searchNothing.isVisible = visible
                    binding.errorLayout.isVisible = false
                },
                showError = { message ->
                    binding.searchRecycler.visibility = View.INVISIBLE
                    binding.searchNothing.isVisible = false
                    binding.errorLayout.isVisible = true
                }
            )
        }
    }
}