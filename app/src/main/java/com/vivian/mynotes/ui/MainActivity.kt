package com.vivian.mynotes.ui

import android.os.Bundle
import com.vivian.mynotes.databinding.ActivityMainBinding
import com.vivian.mynotes.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        var isAnimatedRecyclerView : Boolean = false
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        isAnimatedRecyclerView = true
    }
}