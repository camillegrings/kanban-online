package br.com.cwi.kanbanonline.fragments.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.listeners.OnPostChangeListener
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.utils.BoardHolder.board
import kotlinx.android.synthetic.main.dialog_create_post.*
import kotlinx.android.synthetic.main.dialog_create_post.view.*

class CreatePostDialogFragment  : DialogFragment(), OnPostChangeListener {
    override fun onPostChangeSucceded() {
        Toast.makeText(context, "Post criado.", Toast.LENGTH_SHORT).show()
        FirestoreService.readPosts(board?.id!!)
        dialog.dismiss()
    }

    override fun onPostChangeFailed(e: Exception) {
        Toast.makeText(context, "Erro ao criar post:" + e, Toast.LENGTH_LONG).show()
        dialog.dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_create_post, container, false)
        var boardId = this.arguments?.getString("boardId")
        var sectionId = this.arguments?.getString("sectionId")

        FirestoreService.listenerCreatePost = this

        view.cratePostButton.setOnClickListener {
            val description = descriptionPostEditText.text.toString()
            FirestoreService.createPost(description, boardId!!,sectionId!!)
        }

        return view
    }
}