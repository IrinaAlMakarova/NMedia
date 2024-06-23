package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db_room.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryRoomImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    likedByMe = false,
    share = 0,
    shareByMe = false,
    visibility = 0,
    visibilityByMe = false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    //private val repository: PostRepository = PostRepositoryFileImpl(application)
    //private val repository: PostRepository = PostRepositorySQLiteImpl(
    //    AppDb.getInstance(application).postDao
    //)
    private val repository: PostRepository = PostRepositoryRoomImpl(
        AppDb.getInstance(context = application).postDao()
    )

    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun visibilityById(id: Long) = repository.visibilityById(id)

    fun removeById(id: Long) = repository.removeById(id) //Удаление

    val edited = MutableLiveData(empty)
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun cansel() { //Отмена редактирования текста поста
        edited.value?.let {
            edited.value = empty
            //repository.cansel(it)
        }
    }
}