package com.cellodove.presentation.ui.search

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.cellodove.book.databinding.FragmentSearchBinding
import com.cellodove.presentation.base.BaseFragment
import com.cellodove.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel : MainViewModel by activityViewModels()
    override fun observeViewModel() {

        binding.btSearch.setOnClickListener {
            viewModel.request("android",1,50)
        }
    }
}