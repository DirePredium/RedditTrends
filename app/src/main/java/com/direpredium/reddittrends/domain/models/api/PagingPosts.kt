package com.direpredium.reddittrends.domain.models.api

data class PagingPosts(val dist: Int, val posts: List<Post>, val after: String? = null)