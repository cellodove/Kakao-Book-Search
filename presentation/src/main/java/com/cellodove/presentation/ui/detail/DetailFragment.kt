package com.cellodove.presentation.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cellodove.book.databinding.FragmentDetailBinding
import com.cellodove.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = arguments?.getString("title")
        Toast.makeText(requireContext(),"${arguments?.getString("title")}",Toast.LENGTH_SHORT).show()
    }

    override fun observeViewModel() {

    }
}