package com.direpredium.reddittrends.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
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

class PostsAdapter(
    private val onImageClick: (String, String) -> Unit
) : PagingDataAdapter<Post, PostsAdapter.Holder>(PostsDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val post = getItem(position) ?: return
        val hoursAgo = TimeManager.utcToHoursAgo(post.createUtc)
        holder.binding.apply {
            tAuthor.text = post.author
            tHoursAgo.text = "$hoursAgo hr. ago"
            tTitle.text = post.title
            tNumComments.text = "${post.numComments} comments"
            when(post.thumbnailUrl) {
                "default", "image" -> loadPostPhoto(ivThumbnail, post.imageSource)
                else -> {
                    if(post.thumbnailUrl.startsWith("http")) {
                        loadPostPhoto(ivThumbnail, post.thumbnailUrl)
                    } else {
                        ivThumbnail.visibility = View.GONE
                    }
                }
            }
            ivThumbnail.setOnClickListener {
                if(post.imageSource.isNullOrEmpty()) {
                    val firstMetaImageUrl = post.galleryData?.items?.get(0)?.let { it ->
                        post.mediaMetadata?.get(it.media_id)
                    }
                    if(post.mediaMetadata.isNullOrEmpty() || firstMetaImageUrl?.fullMedia?.url == null) {
                        throw IllegalStateException("Unreachable image opening")
                    }
                    onImageClick(post.name, firstMetaImageUrl.fullMedia.url)
                } else {
                    onImageClick(post.name, post.imageSource)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun loadPostPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        if (url.isNullOrBlank()) {
            Glide.with(context)
                .load(R.drawable.placeholder)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(url)
                .error(R.drawable.ic_image)
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