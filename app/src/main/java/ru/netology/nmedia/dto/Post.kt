package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int,
    var likedByMe: Boolean = false,
    var share: Int,
    var shareByMe: Boolean = false,
    var visibility: Int,
    var visibilityByMe: Boolean = false
)