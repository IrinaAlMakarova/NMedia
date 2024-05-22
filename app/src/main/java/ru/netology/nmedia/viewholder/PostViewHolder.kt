package ru.netology.nmedia.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.counter
import ru.netology.nmedia.adapter.OnLikeListener
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder (
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener
): RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.apply {
            tvAuthor.text = post.author
            tvPublished.text = post.published
            tvContent.text = post.content

            tvCoutLike.text = counter(post.likes)
            tvCountShare.text = counter(post.share)
            tvCountVisibility.text = counter(post.visibility)

            ivLike.setImageResource(
                if(post.likedByMe) R.drawable.favorite_red_24dp else R.drawable.favorite_24dp
            )
            ivLike.setOnClickListener {
                onLikeListener(post)
            }


        }
    }
}

