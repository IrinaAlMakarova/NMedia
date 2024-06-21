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
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewholder.PostViewHolder
import ru.netology.nmedia.viewmodel.PostViewModel

class SinglePostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding=FragmentSinglePostBinding.inflate(inflater, container, false)

        val viewHolder = PostViewHolder(binding.post, object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onVisibility(post: Post) {
                viewModel.visibilityById(post.id)
            }

            //override fun onShare(post: Post) {
            //    viewModel.shareById(post.id)
            //}
            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
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

        viewModel.edited.observe(viewLifecycleOwner){
            if(it.id != 0L){
                findNavController().navigate(R.id.action_singlePostFragment_to_newPostFragment, Bundle().apply { textArg=it.content })
            }
        }

        val  id = arguments?.textArg?.toLong()?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == id } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }
        return binding.root
    }
}