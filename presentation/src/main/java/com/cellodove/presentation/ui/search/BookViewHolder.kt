package com.cellodove.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cellodove.book.databinding.BookListItemBinding
import com.cellodove.domain.model.response.Documents

class BookViewHolder(private val binding:BookListItemBinding) :  RecyclerView.ViewHolder(binding.root){

    fun bind(documents : Documents, searchWord : String){
        showData(documents,searchWord)
    }

    private fun showData(documents : Documents,searchWord : String){
        binding.apply {
            Glide.with(binding.root)
                .load(documents.thumbnail)
                .override(100,100)
                .into(binding.ImageThumbnail)
            //bookName.text = documents.title
            bookPrice.text = documents.price.toString()

            val text = documents.title
            val htmlText = text.replace(searchWord, "<font color='#0be3d8'>$searchWord</font>")
            bookName.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        }
    }

    fun isToggle() : Boolean{
        return binding.likeButton.isChecked
    }

    companion object{
        fun create(parent: ViewGroup): BookViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            return BookViewHolder(BookListItemBinding.inflate(layoutInflater,parent,false))
        }
    }
}