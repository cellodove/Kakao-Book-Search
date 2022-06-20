package com.cellodove.presentation

import android.os.Bundle
import com.cellodove.book.databinding.ActivityMainBinding
import com.cellodove.presentation.base.BaseActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    //private val viewModel: MainViewModel by viewModels


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btSearch.setOnClickListener {
            //viewModel.request(binding.etQuery.text.toString())
        }
    }
}