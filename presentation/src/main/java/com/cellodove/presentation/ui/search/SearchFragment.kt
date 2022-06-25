package com.cellodove.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.cellodove.book.R
import com.cellodove.book.databinding.FragmentSearchBinding
import com.cellodove.domain.model.Documents
import com.cellodove.presentation.base.BaseFragment
import com.cellodove.presentation.ui.main.MainActivity.Companion.CONTENTS
import com.cellodove.presentation.ui.main.MainActivity.Companion.PRICE
import com.cellodove.presentation.ui.main.MainActivity.Companion.PUBLISHER
import com.cellodove.presentation.ui.main.MainActivity.Companion.THUMBNAIL
import com.cellodove.presentation.ui.main.MainActivity.Companion.TITLE
import com.cellodove.presentation.ui.main.MainViewModel
import com.cellodove.presentation.util.decideOnState
import com.cellodove.presentation.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel : MainViewModel by activityViewModels()
    private val searchAdapter = SearchAdapter()
    private var searchJob: Job? = null
    private var oldQuery = ""
    private var newQuery = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.layoutToolbar,"Pay", leftIcon = R.drawable.ic_baseline_favorite_24)
        viewInit()
    }

    override fun onResume() {
        super.onResume()
        searchAdapter.setCheckLike(viewModel.getCheckLikeList())
    }

    override fun observeViewModel() {
        statusList()
    }

    private fun viewInit(){
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.searchRecycler.adapter = searchAdapter
        binding.searchRecycler.addItemDecoration(decoration)

        binding.etQuery.setOnEditorActionListener { _, actionId, _ ->
            newQuery = binding.etQuery.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                when {
                    binding.etQuery.text.toString().isEmpty() -> {
                        binding.inputLayout.error = "텍스트를 입력해 주세요."
                    }
                    newQuery == oldQuery -> Unit
                    else -> {
                        oldQuery = newQuery
                        hideKeyboard()
                        binding.inputLayout.error = null
                        viewModel.deleteAllLikeList()
                        searchBook(binding.etQuery.text.toString())
                        binding.searchRecycler.scrollToPosition(0)
                        searchAdapter.setSearchWord(binding.etQuery.text.toString())
                    }
                }
                true
            } else {
                false
            }
        }

        binding.etQuery.doOnTextChanged { _, _, _, _ ->
            binding.inputLayout.error = null
        }

        binding.inputLayout.setEndIconOnClickListener {
            newQuery = binding.etQuery.text.toString()
            when {
                binding.etQuery.text.toString().isEmpty() -> {
                    binding.inputLayout.error = "텍스트를 입력해 주세요."
                }
                newQuery == oldQuery -> Unit
                else -> {
                    oldQuery = newQuery
                    hideKeyboard()
                    binding.inputLayout.error = null
                    viewModel.deleteAllLikeList()
                    searchBook(binding.etQuery.text.toString())
                    binding.searchRecycler.scrollToPosition(0)
                    searchAdapter.setSearchWord(binding.etQuery.text.toString())
                }
            }
        }

        searchAdapter.setItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onClick(documents: Documents) {
                val bundle = Bundle()
                bundle.putString(TITLE, documents.title)
                bundle.putString(THUMBNAIL, documents.thumbnail)
                bundle.putInt(PRICE, documents.price)
                bundle.putString(PUBLISHER, documents.publisher)
                bundle.putString(CONTENTS, documents.contents)
                findNavController().navigate(R.id.BookDetailsFragment,bundle)
            }
        })

        binding.retryButton.setOnClickListener {
            searchAdapter.retry()
        }
    }

    private fun searchBook(query :String){
        searchJob?.cancel()
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

    private fun statusList() {
        searchAdapter.addLoadStateListener { loadState ->
            loadState.decideOnState(
                adapter = searchAdapter,
                showLoading = { visible ->
                    binding.progressBar.isVisible = visible
                    binding.searchNothing.isVisible = false
                    binding.errorLayout.isVisible = false
                },
                showEmptyState = { visible ->
                    binding.searchNothing.isVisible = visible
                    binding.errorLayout.isVisible = false
                },
                showError = {
                    binding.searchRecycler.visibility = View.INVISIBLE
                    binding.searchNothing.isVisible = false
                    binding.errorLayout.isVisible = true
                }
            )
        }
    }

    private fun hideKeyboard(){
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(binding.etQuery.windowToken, 0)
    }
}