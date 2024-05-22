package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter {
    private val onLikeListener: OnLikeListener):RecyclerView.Adapter<PostViewHolder>()
    {
        var list = emptyList<Post>()
        set(values) {
            field = values
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val binding =
                CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding, onLikeListener)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post = list[postion]
            holder.bind(post)
        }

        override fun getItemCount(): Int = list.size
    }





