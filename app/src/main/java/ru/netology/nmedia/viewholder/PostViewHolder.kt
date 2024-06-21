package ru.netology.nmedia.viewholder

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.counter
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        val apply = binding.apply {
            tvAuthor.text = post.author
            tvPublished.text = post.published
            tvContent.text = post.content

            //tvCoutLike.text = counter(post.likes)
            ivLike.text = counter(post.likes)
            //tvCountShare.text = counter(post.share)
            ivShare.text = counter(post.share)
            tvCountVisibility.text = counter(post.visibility)

            //ivLike.setImageResource(
            //    if (post.likedByMe) R.drawable.favorite_red_24dp else R.drawable.favorite_24dp
            //)

            ivLike.isChecked = post.likedByMe
            ivLike.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            ivVisibility.setOnClickListener {
                onInteractionListener.onVisibility(post)
            }

            ivShare.text = counter(post.share)
            ivShare.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            /*
                        ibMenu.setOnClickListener{
                            PopupMenu(it.context,it).apply {
                                inflate(R.menu.options_post)
                                setOnMenuItemClickListener {item->
                                    when(item.itemId){
                                        R.id.remove ->{
                                            onRemoveListener(post)
                                            true
                                        }
                                        else -> false
                                    }
                                }
                            }.show()
                        }
            */

            ibMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            ivVideo.setOnClickListener {
                onInteractionListener.onVideo(post)
            }
            ivPlay.setOnClickListener {
                onInteractionListener.onVideo(post)
            }

            if (post.link == null) {
                groupVideo.visibility = View.GONE // перестаёт занимать место на экране
            } else {
                groupVideo.visibility = View.VISIBLE
            }

        }
    }
}

