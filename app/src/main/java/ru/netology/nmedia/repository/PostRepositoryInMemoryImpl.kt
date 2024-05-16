package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likes = 909_800_300,
        likedByMe = false,
        share = 15_300,
        shareByMe = false,
        visibility = 3,
        visibilityByMe = false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if (post.likedByMe) post.likes++ else post.likes--
        data.value = post
    }

    override fun share() {
        post = post.copy(shareByMe = !post.shareByMe)
        if (post.shareByMe) post.share++ else post.share--
        data.value = post
    }

    override fun visibility() {
        post = post.copy(visibilityByMe = !post.visibilityByMe)
        if (post.visibilityByMe) post.visibility++ else post.visibility--
        data.value = post
    }

    override fun counter(count: Int): String {
        val allCount = if (count in 1_000..9_999) {
            "${(count / 100) / 10.0}K"
        } else if (count in 10_000..999_999) {
            "${(count / 1_000)}K"
        } else if (count >= 1_000_000) {
            "${(count / 100_000) / 10.0}M"
        } else {
            "$count"
        }
        return allCount
    }
}