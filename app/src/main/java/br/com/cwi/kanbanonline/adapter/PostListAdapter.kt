package br.com.cwi.kanbanonline.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.services.models.Post
import kotlinx.android.synthetic.main.post_list_item.view.*

/**
 * @author eduardo.melzer
 */
class PostListAdapter(
        private val posts: List<Post>,
        private val context: Context,
        private val onClick: (post: Post) -> Unit) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bindView(post, context)

        holder.itemView.setOnClickListener {
            onClick(post)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.post_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(post: Post, context: Context) {
            val content = itemView.postContent

            content.text = post.description
        }

    }
}