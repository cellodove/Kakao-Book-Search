package com.cellodove.presentation.ui.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.cellodove.domain.model.Documents

class SearchAdapter : PagingDataAdapter<Documents,BookViewHolder>(DOCUMENTS_COMPARATOR) {
    private val likeList  = arrayListOf<String>()
    private var searchWord : String = ""
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onClick(documents : Documents)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    fun setSearchWord(word : String){
        this.searchWord = word
    }

    fun setCheckLike(likeList : ArrayList<String>){
        this.likeList.clear()
        this.likeList.addAll(likeList)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.bind(item,searchWord,likeList)
            holder.itemView.setOnClickListener {
                itemClickListener.onClick(getItem(position)!!)
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
            override fun areItemsTheSame(oldItem: Documents, newItem: Documents) = oldItem.isbn == newItem.isbn && oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Documents, newItem: Documents) = oldItem == newItem
        }
    }
}