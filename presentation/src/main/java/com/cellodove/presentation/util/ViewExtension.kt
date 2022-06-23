package com.cellodove.presentation.util

import android.view.View
import androidx.annotation.DrawableRes
import com.cellodove.book.databinding.LayoutToolbarBinding

fun initToolbar(
    toolbar: LayoutToolbarBinding,
    title: String? = null,
    @DrawableRes leftIcon: Int? = null,
    leftClick: (() -> Unit)? = null,
) {
    toolbar.apply {
        tvTitle.text = title
        ivLeft.setImageResource(leftIcon ?: 0)
        ivLeft.visibility = if (leftIcon == null) View.GONE else View.VISIBLE
        ivLeft.setOnClickListener {
            leftClick?.invoke()
        }
        tvLeft.setOnClickListener {
            leftClick?.invoke()
        }
    }
}