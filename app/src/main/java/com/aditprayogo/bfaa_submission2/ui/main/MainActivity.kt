package com.aditprayogo.bfaa_submission2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditprayogo.bfaa_submission2.R
import com.aditprayogo.bfaa_submission2.data.responses.SearchResponseItem
import com.aditprayogo.bfaa_submission2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(applicationContext)
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val userItems: MutableList<SearchResponseItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        searchUser()
        initObservers()
        initRecyclerView()
    }

    private fun initToolbar() {
        supportActionBar?.elevation = 0f
    }

    private fun searchUser() {
        binding.apply {
            svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.isNotEmpty()) {
                            userItems.clear()
                            mainViewModel.getUsersFromApi(query)
                            svUser.clearFocus()
                        } else {
                            svUser.clearFocus()
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false

            })
        }
    }

    private fun initRecyclerView() {
        binding.rvUser.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mainAdapter
        }
        mainAdapter.setActivity(this)
    }

    private fun initObservers() {
        with(mainViewModel) {
            state.observe(this@MainActivity, {
                it?.let {  }
            })
            resultFromApi.observe(this@MainActivity, {
                it?.let { handleStateFromInternet(it) }
            })
        }
    }

    private fun handleStateLoading() {

    }

    private fun handleStateFromInternet(result : List<SearchResponseItem>) {
        userItems.clear()
        userItems.addAll(result)
        mainAdapter.setItems(userItems)
    }
}