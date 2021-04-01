package com.aditprayogo.bfaa_submission2.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditprayogo.bfaa_submission2.R
import com.aditprayogo.bfaa_submission2.core.util.loadCircleCrop
import com.aditprayogo.bfaa_submission2.data.responses.DetailUserResponse
import com.aditprayogo.bfaa_submission2.databinding.ActivityDetailBinding
import com.aditprayogo.bfaa_submission2.ui.viewpager.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val USERNAME_KEY = "username_key"
    }

    private val detailViewModel : DetailViewModel by viewModels()
    private var username : String? = null

    private val binding : ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleIntent()
        initObservers()
        fetchData()
        initToolbar()
        initPageAdapter()
    }

    private fun handleIntent() {
        username = intent.getStringExtra(USERNAME_KEY)
    }

    private fun initObservers() {
        with(detailViewModel) {
            resultUserDetail.observe(this@DetailActivity,{ handleStateFromInternet(it) })
        }
    }

    private fun handleStateFromInternet(data : DetailUserResponse) {
        binding.apply {
            ivUser.loadCircleCrop(data.avatarUrl)
            txtUsername.text = data.login
            txtBio.text = data.bio ?: getString(R.string.no_bio)
            txtFollower.text = data.followers.toString()
            txtFollowing.text = data.following.toString()
            txtRepository.text = data.publicRepos.toString()
        }
    }

    private fun fetchData() {
        username?.let {
            detailViewModel.getUserDetailFromApi(it)
        }
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            elevation = 0f
            title = username
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initPageAdapter() {
        val sectionPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    fun getUsername() : String? {
        return username
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}