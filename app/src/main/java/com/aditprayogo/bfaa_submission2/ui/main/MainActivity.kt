package com.aditprayogo.bfaa_submission2.ui.main

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditprayogo.bfaa_submission2.R
import com.aditprayogo.bfaa_submission2.core.state.LoaderState
import com.aditprayogo.bfaa_submission2.core.util.setGone
import com.aditprayogo.bfaa_submission2.core.util.setVisible
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
        supportActionBar?.apply {
            elevation = 0f
            title = getString(R.string.github_uzerz)
        }
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
                LinearLayoutManager(
                    this@MainActivity, LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = mainAdapter
        }
        mainAdapter.setActivity(this)
    }

    private fun initObservers() {
        with(mainViewModel) {
            state.observe(this@MainActivity, {
                it?.let { handleStateLoading(it) }
            })
            resultFromApi.observe(this@MainActivity, {
                it?.let { handleStateUserResult(it) }
            })
            networkError.observe(this@MainActivity, {
                it?.let { handleStateErrorInternet(it) }
            })
        }
    }

    private fun handleStateLoading(loading: LoaderState) {
        if (loading == LoaderState.ShowLoading) {
            binding.apply {
                shimmerLoading.root.setVisible()
                rvUser.setGone()
            }
        } else {
            binding.apply {
                shimmerLoading.root.setGone()
                rvUser.setVisible()
            }

        }
    }

    private fun handleStateErrorInternet(error : Boolean) {
        if (error) {
            binding.apply {
                shimmerLoading.root.setVisible()
                rvUser.setGone()
            }
        } else {
            binding.apply {
                shimmerLoading.root.setGone()
                rvUser.setVisible()
            }
        }
    }

    private fun handleStateUserResult(result: List<SearchResponseItem>) {
        userItems.clear()
        userItems.addAll(result)
        mainAdapter.setItems(userItems)
    }
}