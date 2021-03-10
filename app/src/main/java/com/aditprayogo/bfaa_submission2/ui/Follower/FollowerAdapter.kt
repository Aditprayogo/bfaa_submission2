package com.aditprayogo.bfaa_submission2.ui.Follower

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditprayogo.bfaa_submission2.R
import com.aditprayogo.bfaa_submission2.core.util.load
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponseItem
import com.aditprayogo.bfaa_submission2.databinding.ItemRowUserBinding
import com.aditprayogo.bfaa_submission2.ui.detail.DetailActivity

class FollowerAdapter(
    private val context: Context
) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    private var userFollowerItems: MutableList<FollowerResponseItem> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowUserBinding = ItemRowUserBinding.bind(itemView)

        fun bind(data: FollowerResponseItem) {
            with(itemView) {
                binding.apply {
                    imgUser.load(data.avatarUrl)
                    txtUsername.text = data.login
                }
                setOnClickListener {
                    context.startActivity(
                        Intent(
                            context,
                            DetailActivity::class.java
                        ).apply {
                            putExtra(DetailActivity.USERNAME_KEY, data.login)
                        }
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_row_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FollowerAdapter.ViewHolder, position: Int) {
        holder.bind(userFollowerItems[position])
    }

    override fun getItemCount(): Int = userFollowerItems.size

    fun setItems(data: MutableList<FollowerResponseItem>) {
        this.userFollowerItems = data
        notifyDataSetChanged()
    }
}