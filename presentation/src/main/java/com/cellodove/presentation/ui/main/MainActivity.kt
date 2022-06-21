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
        viewModel.userLiveData.observe(this){
            val fragment = when(it){
                MainViewModel.FragmentStep.SEARCH_BOOK -> SearchFragment()

                MainViewModel.FragmentStep.BOOK_DETAIL -> DetailFragment()
            }
            setFragment(R.id.fragmentContainer, fragment,true)
        }
    }

    private fun setFragment(layout: Int, child: Fragment, isAddStack: Boolean){
        val childFt = supportFragmentManager.beginTransaction()
        if (isAddStack){
            if (!child.isAdded) {
//                childFt.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_exit_to_left, R.anim.anim_enter_from_left, R.anim.anim_slide_out_right)
                childFt.replace(layout, child)
                childFt.addToBackStack(null)
            }
        }else{
            if (!child.isAdded) {
                childFt.replace(layout, child)
//                 childFt.addToBackStack(null)
            }
        }
        childFt.commit()
    }
}