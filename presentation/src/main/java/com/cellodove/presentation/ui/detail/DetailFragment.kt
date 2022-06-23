package com.cellodove.presentation.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.cellodove.book.R
import com.cellodove.book.databinding.FragmentDetailBinding
import com.cellodove.presentation.base.BaseFragment
import com.cellodove.presentation.ui.main.MainViewModel
import com.cellodove.presentation.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel : MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(
            binding.layoutToolbar,
            "도서 상세",
            leftIcon = R.drawable.ic_baseline_keyboard_arrow_left_30,
            leftClick = {
                requireActivity().onBackPressed()
            }
        )

        val title = arguments?.getString("title") ?: ""
        val isLike = viewModel.getCheckLikeList().contains(title)
        val thumbnail = arguments?.getString("thumbnail") ?: ""
        val price = arguments?.getInt("price") ?: 0
        val publisher = arguments?.getString("publisher") ?: ""
        val contents = arguments?.getString("contents") ?: ""

        binding.apply {
            if (thumbnail.isNotEmpty()){
                Glide.with(binding.root)
                    .load(thumbnail)
                    .override(200,200)
                    .into(binding.thumbnail)
            }else{
                binding.thumbnail.setImageResource(R.drawable.ic_baseline_image_not_supported_200)
            }
            bookName.text = title
            val decimal = DecimalFormat("#,###")
            bookPrice.text = "${decimal.format(price)}원"
            bookPublisher.text = publisher
            bookContent.text = contents
            likeButton.isChecked = isLike
            likeButton.setOnClickListener {
                if (likeButton.isChecked){
                    viewModel.addCheckLike(title)
                }else{
                    viewModel.deleteCheckLike(title)
                }
            }
        }
    }

    override fun observeViewModel() = Unit
}