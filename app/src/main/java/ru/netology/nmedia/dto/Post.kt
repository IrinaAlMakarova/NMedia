package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val likedByMe: Boolean,
    val share: Int,
    val shareByMe: Boolean,
    val visibility: Int,
    val visibilityByMe: Boolean,
    val link: String? = null
)