package com.cellodove.presentation.ui.main

import com.cellodove.book.databinding.ActivityMainBinding
import com.cellodove.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun observeViewModel() = Unit


    companion object{
        const val TITLE = "title"
        const val THUMBNAIL = "thumbnail"
        const val PRICE = "price"
        const val PUBLISHER = "publisher"
        const val CONTENTS = "contents"
    }
}