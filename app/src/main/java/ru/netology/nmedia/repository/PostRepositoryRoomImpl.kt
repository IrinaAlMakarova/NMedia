package ru.netology.nmedia.repository

import androidx.lifecycle.map
import ru.netology.nmedia.dao_room.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val dao: PostDao,
) : PostRepository {

    override fun getAll() = dao.getAll().map { list ->
        list.map {
            it.toDto()
        }
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun visibilityById(id: Long) {
        dao.visibilityById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}