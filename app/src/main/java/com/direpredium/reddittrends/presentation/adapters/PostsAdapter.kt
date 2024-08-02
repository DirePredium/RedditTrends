package com.direpredium.reddittrends.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.direpredium.reddittrends.R
import com.direpredium.reddittrends.data.util.TimeManager
import com.direpredium.reddittrends.databinding.ItemPostBinding
import com.direpredium.reddittrends.domain.models.api.Post

class PostsAdapter : PagingDataAdapter<Post, PostsAdapter.Holder>(PostsDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val post = getItem(position) ?: return
        val hoursAgo = TimeManager.utcToHoursAgo(post.createUtc)
        with (holder.binding) {
            tAuthor.text = post.author
            tHoursAgo.text = "$hoursAgo hr. ago"
            tTitle.text = post.title
            tNumComments.text = "${post.numComments} comments"
            loadPostPhoto(ivThumbnail, post.thumbnailUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun loadPostPhoto(imageView: ImageView, url: String) {
        val context = imageView.context
        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.placeholder)
                .into(imageView)
        }
    }

    class Holder(
        val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root)

}

class PostsDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}