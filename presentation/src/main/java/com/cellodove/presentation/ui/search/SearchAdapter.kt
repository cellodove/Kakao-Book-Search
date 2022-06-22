package com.cellodove.presentation.ui.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.cellodove.domain.model.response.Documents

class SearchAdapter : PagingDataAdapter<Documents,BookViewHolder>(DOCUMENTS_COMPARATOR) {

    private lateinit var itemClickListener : OnItemClickListener
    interface OnItemClickListener {
        fun onClick(documents : Documents, isLike : Boolean)
    }

    private var searchWord : String = ""

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setSearchWord(word : String){
        this.searchWord = word
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.bind(item,searchWord)
            holder.itemView.setOnClickListener {
                itemClickListener.onClick(getItem(position)!!,holder.isToggle())
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object{
        private val DOCUMENTS_COMPARATOR = object : DiffUtil.ItemCallback<Documents>(){
            override fun areItemsTheSame(oldItem: Documents, newItem: Documents) = oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Documents, newItem: Documents) = oldItem == newItem
        }
    }
}