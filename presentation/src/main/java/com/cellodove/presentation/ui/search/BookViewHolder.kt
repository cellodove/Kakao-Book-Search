package com.cellodove.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cellodove.book.databinding.BookListItemBinding
import com.cellodove.domain.model.response.Documents

class BookViewHolder(private val binding:BookListItemBinding) :  RecyclerView.ViewHolder(binding.root){

    fun bind(documents : Documents){
        showData(documents)
    }

    private fun showData(documents : Documents){
        binding.apply {
            Glide.with(binding.root)
                .load(documents.thumbnail)
                .override(100,100)
                .into(binding.ImageThumbnail)
            bookName.text = documents.title
            bookPrice.text = documents.price.toString()
        }
    }

    fun isToggle() : Boolean{
        return binding.likeButton.isChecked
    }

    companion object{
        fun create(parent: ViewGroup): BookViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            return BookViewHolder(BookListItemBinding.inflate(layoutInflater))
        }
    }
}