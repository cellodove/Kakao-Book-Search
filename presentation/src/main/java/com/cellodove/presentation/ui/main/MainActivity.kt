package com.cellodove.presentation.ui.main

import com.cellodove.book.databinding.ActivityMainBinding
import com.cellodove.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun observeViewModel() = Unit

}