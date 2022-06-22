package com.cellodove.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.cellodove.book.R
import com.cellodove.book.databinding.ActivityMainBinding
import com.cellodove.presentation.base.BaseActivity
import com.cellodove.presentation.ui.detail.DetailFragment
import com.cellodove.presentation.ui.search.SearchFragment

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun observeViewModel() {

    }

}