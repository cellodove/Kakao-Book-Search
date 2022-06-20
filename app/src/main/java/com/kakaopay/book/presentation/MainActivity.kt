package com.kakaopay.book.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kakaopay.book.data.BookRemoteDataSource
import com.kakaopay.book.data.BookRepositoryImpl
import com.kakaopay.book.databinding.ActivityMainBinding
import com.kakaopay.book.di.NetworkModule

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            BookRepositoryImpl(
                NetworkModule.createRetrofit(
                    BookRemoteDataSource::class.java
                )
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        binding.btSearch.setOnClickListener {
            viewModel.request(binding.etQuery.text.toString())
        }
    }
}