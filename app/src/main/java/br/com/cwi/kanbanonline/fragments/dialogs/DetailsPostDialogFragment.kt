package br.com.cwi.kanbanonline.fragments.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.utils.BoardHolder
import br.com.cwi.kanbanonline.utils.UserHolder
import kotlinx.android.synthetic.main.dialog_details_post.view.*

class DetailsPostDialogFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_details_post, container, false)

        val details = view.details_post
        details.text = this.arguments?.getString("description")

        val createdBy = view.created_by_post

        if(BoardHolder.board?.anonymous == true || BoardHolder.board?.createdBy == UserHolder.user?.email){
            createdBy.text = this.arguments?.getString("createdBy")
        } else {
            createdBy.text = ""
        }

        return view
    }
}