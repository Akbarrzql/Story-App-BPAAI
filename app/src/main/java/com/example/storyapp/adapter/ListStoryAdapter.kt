package com.example.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.view.home.DetailActivity
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.storyapp.data.local.entity.Story
import com.example.storyapp.databinding.StoryLayoutBinding

class ListStoryAdapter : PagingDataAdapter<Story, ListStoryAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(var binding: StoryLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(StoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        holder.binding.apply {
            itemName.text = story?.name
            itemDetail.text = story?.description
            Glide.with(holder.itemView)
                .load(story?.photoUrl)
                .into(imgItemPhoto)

            root.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        root.context as Activity,
                        Pair(itemName, "name"),
                        Pair(itemDetail, "description"),
                        Pair(imgItemPhoto, "image"),
                    )
                Intent(root.context, DetailActivity::class.java).also { intent ->
                    intent.putExtra(DetailActivity.EXTRA_STORY, story)
                    root.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}