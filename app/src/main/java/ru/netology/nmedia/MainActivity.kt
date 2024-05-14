package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 0,
            likedByMe = false,
            share = 0,
            shareByMe = false,
            visibility = 0,
            visibilityByMe = false
        )

        with(binding) {
            tvAuthor!!.text = post.author
            tvPublished!!.text = post.published
            tvContent!!.text = post.content
            if (post.likedByMe) {
                ivLike?.setImageResource(R.drawable.favorite_red_24dp)
            }
            tvCoutLike!!.text = post.likes.toString()
            tvCountShare!!.text = post.share.toString()
            tvCountVisibility!!.text = post.visibility.toString()


            ivLike?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                ivLike.setImageResource(
                    if (post.likedByMe) R.drawable.favorite_red_24dp else R.drawable.favorite_24dp
                )

                if (post.likedByMe) post.likes++ else post.likes--
                tvCoutLike.text = counter(post.likes).toString()
            }

            ivShare?.setOnClickListener {
                post.shareByMe = !post.shareByMe
                if (post.shareByMe) post.share++ else post.share--
                tvCountShare.text = counter(post.share).toString()
            }

            ivVisibility?.setOnClickListener {
                post.visibilityByMe = !post.visibilityByMe
                if (post.visibilityByMe) post.visibility++ else post.visibility--
                tvCountVisibility.text = counter(post.visibility).toString()
            }
        }

        /*
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         */
    }

    private fun counter(count: Int): String {
        val a = if (count in 999..1_099) {
            "1K"
        } else if (count in 1100..1_199) {
            "1.1K"
        } else if (count in 1200..1_299) {
            "1.2"
        } else if (count in 1300..1_399) {
            "1.3"
        } else if (count in 1400..1_499) {
            "1.4"
        } else if (count in 1500..1_599) {
            "1.5"
        } else if (count in 1600..1_699) {
            "1.6"
        } else if (count in 1700..1_799) {
            "1.7"
        } else if (count in 1800..1_899) {
            "1.8"
        } else if (count in 1900..1_999) {
            "1.9"
        } else if (count in 10_000..999_999) {
            ""
        } else if (count > 1_000_000) {
            "1.3M"
        } else {
            count
        }
        return a.toString()
    }
}