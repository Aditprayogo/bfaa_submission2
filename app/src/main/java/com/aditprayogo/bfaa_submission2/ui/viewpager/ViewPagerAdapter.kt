package com.aditprayogo.bfaa_submission2.ui.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aditprayogo.bfaa_submission2.R
import com.aditprayogo.bfaa_submission2.ui.Follower.FollowerFragment
import com.aditprayogo.bfaa_submission2.ui.Following.FollowingFragment

class ViewPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val TAB_TITLES = intArrayOf(R.string.follower, R.string.following)

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

}