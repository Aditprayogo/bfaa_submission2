package com.aditprayogo.bfaa_submission2.ui.Follower

import android.content.Loader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditprayogo.bfaa_submission2.R
import com.aditprayogo.bfaa_submission2.core.state.LoaderState
import com.aditprayogo.bfaa_submission2.core.util.setGone
import com.aditprayogo.bfaa_submission2.core.util.setVisible
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponseItem
import com.aditprayogo.bfaa_submission2.databinding.FragmentFollowerBinding
import com.aditprayogo.bfaa_submission2.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment() {

    private val followerViewModel : FollowerViewModel by viewModels()
    private val userFollowerList : MutableList<FollowerResponseItem> = mutableListOf()

    private val binding : FragmentFollowerBinding by lazy {
        FragmentFollowerBinding.inflate(layoutInflater)
    }

    private val followerAdapter : FollowerAdapter by lazy {
        FollowerAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUsername()
        initObservers()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = followerAdapter
        }
    }

    private fun handleUsername() {
        val activity = activity as DetailActivity
        val username: String? = activity.getUsername()
        username?.let { followerViewModel.getUserFollowers(it) }
    }

    private fun initObservers() {
        with(followerViewModel) {
            state.observe(viewLifecycleOwner, { handleStateLoading(it) })
            resultUserFollower.observe(viewLifecycleOwner, { handleUserFollower(it) })
        }
    }

    private fun handleStateLoading(loading : LoaderState) {
        if (loading is LoaderState.ShowLoading) {
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

    private fun handleUserFollower(data : List<FollowerResponseItem>) {
        handleEmptyFollower(data)
        userFollowerList.clear()
        userFollowerList.addAll(data)
        followerAdapter.setItems(userFollowerList)
    }

    private fun handleEmptyFollower(data : List<FollowerResponseItem>) {
        if (data.isEmpty()) {
            setIllustration(true)
            binding.rvUser.setGone()
        } else {
            setIllustration(false)
            binding.rvUser.setVisible()
        }
    }

    private fun setIllustration(status : Boolean) {
        binding.lottieView.root.visibility = if (status) VISIBLE else GONE
    }


}