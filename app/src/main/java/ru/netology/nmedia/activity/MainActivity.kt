package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                tvAuthor!!.text = post.author
                tvPublished!!.text = post.published
                tvContent!!.text = post.content
                tvCoutLike!!.text = counter(post.likes)
                tvCountShare!!.text = counter(post.share)
                tvCountVisibility!!.text = counter(post.visibility)

                ivLike!!.setImageResource(
                    if (post.likedByMe) R.drawable.favorite_red_24dp else R.drawable.favorite_24dp
                )
            }
        }
        binding.ivLike?.setOnClickListener {
            viewModel.like()
        }

        binding.ivShare?.setOnClickListener {
            viewModel.share()
        }

        binding.ivVisibility?.setOnClickListener {
            viewModel.visibility()
        }
    }
}


/*
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
            //likes = 909_700,
            likedByMe = false,
            //share = 1_300,
            shareByMe = false,
            //visibility = 1_500_000,
            visibilityByMe = false
        )

        with(binding) {
            tvAuthor!!.text = post.author
            tvPublished!!.text = post.published
            tvContent!!.text = post.content
            if (post.likedByMe) {
                ivLike?.setImageResource(R.drawable.favorite_red_24dp)
            }
            tvCoutLike!!.text = counter(post.likes).toString()
            tvCountShare!!.text = counter(post.share).toString()
            tvCountVisibility!!.text = counter(post.visibility).toString()


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
        /*
            val allCount = if (count in 999..1_099) {
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
            } else if (count >= 1_000_000) {
                "1.3M"
            } else {
                count
            }
            return allCount.toString()
         */
    }
}

 */

