package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>
    fun save(post: Post)
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun visibilityById(id: Long)
    fun removeById(id: Long) //Удаление поста
    //fun cansel(post: Post) //Отмена редактирования текста поста
}