package com.cellodove.presentation.ui.search

import android.app.PendingIntent.getActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cellodove.book.R
import com.cellodove.book.databinding.BookListItemBinding
import com.cellodove.domain.model.response.Documents
import java.text.DecimalFormat

class BookViewHolder(private val binding:BookListItemBinding) :  RecyclerView.ViewHolder(binding.root){

    fun bind(documents : Documents, searchWord : String, isLikeList : ArrayList<String>){
        showData(documents,searchWord,isLikeList)
    }

    private lateinit var likeButtonClickListener : OnLikeButtonClickListener
    interface OnLikeButtonClickListener {
        fun onClick(isLike : Boolean, title : String)
    }

    fun setLikeButtonClickListener(onLikeButtonClickListener: OnLikeButtonClickListener) {
        this.likeButtonClickListener = onLikeButtonClickListener
    }

    private fun showData(documents : Documents,searchWord : String,isLikeList : ArrayList<String>){
        binding.apply {
            if (documents.thumbnail.isNotEmpty()){
                Glide.with(binding.root)
                    .load(documents.thumbnail)
                    .override(100,100)
                    .into(binding.ImageThumbnail)
            }else{
                binding.ImageThumbnail.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
            }
            val decimal = DecimalFormat("#,###")
            bookPrice.text = "${decimal.format(documents.price)}원"
            val text = documents.title
            val htmlText = text.replace(searchWord, "<font color='#0be3d8'>$searchWord</font>")
            bookName.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.likeButton.isChecked = isLikeList.contains(documents.title)
            binding.likeButton.setOnClickListener {
                likeButtonClickListener.onClick(binding.likeButton.isChecked,documents.title)
            }
        }
    }

    companion object{
        fun create(parent: ViewGroup): BookViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            return BookViewHolder(BookListItemBinding.inflate(layoutInflater,parent,false))
        }
    }
}