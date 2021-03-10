package com.aditprayogo.bfaa_submission2.ui.Following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditprayogo.bfaa_submission2.core.state.LoaderState
import com.aditprayogo.bfaa_submission2.core.util.setGone
import com.aditprayogo.bfaa_submission2.core.util.setVisible
import com.aditprayogo.bfaa_submission2.data.responses.FollowingResponseItem
import com.aditprayogo.bfaa_submission2.databinding.FragmentFollowingBinding
import com.aditprayogo.bfaa_submission2.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private val followingViewModel : FollowingViewModel by viewModels()
    private val userFollowingList : MutableList<FollowingResponseItem> = mutableListOf()

    private val binding : FragmentFollowingBinding by lazy {
        FragmentFollowingBinding.inflate(layoutInflater)
    }

    private val followingAdapter : FollowingAdapter by lazy {
        FollowingAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUsername()
        initObservers()
        initRecyclerView()
    }

    private fun handleUsername() {
        val activity = activity as DetailActivity
        val username: String? = activity.getUsername()
        username?.let { followingViewModel.getUserFollowing(it) }
    }

    private fun initObservers() {
        with(followingViewModel) {
            state.observe(viewLifecycleOwner, { handleStateLoading(it) })
            resultUserFollowing.observe(viewLifecycleOwner, { handleUserFollowing(it) })
        }
    }

    private fun initRecyclerView() {
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = followingAdapter
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

    private fun handleUserFollowing(data : List<FollowingResponseItem>) {
        handleEmptyFollowing(data)
        userFollowingList.clear()
        userFollowingList.addAll(data)
        followingAdapter.setItems(userFollowingList)
    }

    private fun handleEmptyFollowing(data : List<FollowingResponseItem>) {
        if (data.isEmpty()) {
            setIllustration(true)
            binding.rvUser.setGone()
        } else {
            setIllustration(false)
            binding.rvUser.setVisible()
        }
    }

    private fun setIllustration(status : Boolean) {
        binding.lottieView.root.visibility = if (status) View.VISIBLE else View.GONE
    }

}