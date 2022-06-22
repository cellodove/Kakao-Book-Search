package com.cellodove.presentation.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cellodove.book.databinding.FragmentDetailBinding
import com.cellodove.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(),"${arguments?.getString("title")}",Toast.LENGTH_SHORT).show()
        val isLike = arguments?.getBoolean("isLike")
        val title = arguments?.getString("title")
        val thumbnail = arguments?.getString("thumbnail")
        val price = arguments?.getString("price")
        val publisher = arguments?.getString("publisher")
        val contents = arguments?.getString("contents")

        binding.apply {
            Glide.with(binding.root)
                .load(thumbnail)
                .override(200,200)
                .into(binding.thumbnail)
            likeButton.isChecked = isLike ?: false
            bookName.text = title
            bookPrice.text = "$price 원"
            bookPublisher.text = publisher
            bookContent.text = contents
        }
    }

    override fun observeViewModel() = Unit
}