package com.aditprayogo.bfaa_submission2.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.aditprayogo.bfaa_submission2.R
import com.aditprayogo.bfaa_submission2.core.util.load
import com.aditprayogo.bfaa_submission2.data.responses.SearchResponseItem
import com.aditprayogo.bfaa_submission2.databinding.ItemRowUserBinding
import com.aditprayogo.bfaa_submission2.ui.detail.DetailActivity

class MainAdapter(
    private val context: Context
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var userItems: MutableList<SearchResponseItem> = mutableListOf()
    private lateinit var mainActivity: MainActivity

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowUserBinding = ItemRowUserBinding.bind(itemView)

        fun bind(data: SearchResponseItem) {
            with(itemView) {
                binding.apply {
                    imgUser.load(data.avatarUrl)
                    txtUsername.text = data.login
                }
                setOnClickListener {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.USERNAME_KEY, data.login)
                        }
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_row_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(userItems[position])
    }

    override fun getItemCount(): Int = userItems.size

    fun setActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    fun setItems(data: MutableList<SearchResponseItem>) {
        this.userItems = data
        notifyDataSetChanged()
    }

    fun clearItems() {
        userItems.clear()
        notifyDataSetChanged()
    }
}