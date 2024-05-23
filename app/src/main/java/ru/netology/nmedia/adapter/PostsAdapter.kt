package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewholder.PostViewHolder

typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter (
    private val onLikeListener: OnLikeListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}
=======
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





>>>>>>> 98f4356870b5f7e66d3833bb466a82d3fb33364d
