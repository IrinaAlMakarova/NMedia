package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

        /*
        val adapter = PostsAdapter({
            viewModel.likeById(it.id)
        }, {
            viewModel.shareById(it.id)
        }, {
                viewModel.removeById(it.id)
        })
        */
        val adapter = PostsAdapter(onInteractionListener = object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            //override fun onShare(post: Post) {
            //    viewModel.shareById(post.id)
            //}
            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            //-- Video
            override fun onVideo(post: Post) {
                val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.link))
                startActivity(intentVideo)
            }
            //-- Video

            //-- Edit (регистрация контракта)
            val editPostLauncher =
                registerForActivityResult(EditPostResultContract()) { result ->
                    result ?: return@registerForActivityResult
                    viewModel.changeContent(result)
                    viewModel.save()
                }
            //-- Edit

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                editPostLauncher.launch(post.content)  //-- вызов функции launch к контракту
            }

            //override fun onEdit(post: Post) {
            //    val intentEdit = Intent(this@MainActivity, EditPostActivity::class.java)
            //    startActivity(intentEdit)
            //}

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })

        // New Post
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        // New Post

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        //binding.groupCansel.visibility = View.GONE // перестаёт занимать место на экране

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            // Добавление поста с помощью EditText (EditText и кнопка save)
            /*
            with(binding.content) {
                requestFocus()
                setText(post.content)
            }

            with(binding.contentNotEdit) {
                clearFocus()
                setText(post.content)
            }
            */
            // Добавление поста с помощью EditText (EditText и кнопка save)

            //binding.groupCansel.visibility = View.VISIBLE
        }

        // Добавление поста с помощью EditText (EditText и кнопка save)
        /*
        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
            binding.groupCansel.visibility = View.GONE // перестаёт занимать место на экране
        }
        */
        // Добавление поста с помощью EditText (EditText и кнопка save)

        // Редактирование поста с помощью EditText (EditText и кнопка cansel)
        /*
        binding.cancel.setOnClickListener {
            with(binding.contentNotEdit) {

                viewModel.cansel()

                setText("")
                binding.content.setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
            binding.groupCansel.visibility = View.GONE // перестаёт занимать место на экране
        }
        */
        // Редактирование поста с помощью EditText (EditText и кнопка cansel)

        return binding.root
    }

}


/*
package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { posts ->
            posts.map { post ->
                CardPostBinding.inflate(layoutInflater, binding.container, false).apply {
                    tvAuthor.text = post.author
                    tvPublished.text = post.published
                    tvContent.text = post.content
                    tvCoutLike.text = counter(post.likes)
                    tvCountShare.text = counter(post.share)
                    tvCountVisibility.text = counter(post.visibility)

                    ivLike.setImageResource(
                        if (post.likedByMe) R.drawable.favorite_red_24dp else R.drawable.favorite_24dp
                    )

                    ivLike.setOnClickListener {
                        viewModel.likeById(post.id)
                    }

                    ivShare.setOnClickListener {
                        viewModel.shareById(post.id)
                    }

                    ivVisibility.setOnClickListener {
                        viewModel.visibilityById(post.id)
                    }

                }.root
            }.forEach {
                binding.container.addView(it)
            }
        }
    }
}
 */

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

