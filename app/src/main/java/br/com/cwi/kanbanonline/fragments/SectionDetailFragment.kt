package br.com.cwi.kanbanonline.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.adapter.PostListAdapter
import br.com.cwi.kanbanonline.fragments.dialogs.CreatePostDialogFragment
import br.com.cwi.kanbanonline.fragments.dialogs.DetailsPostDialogFragment
import br.com.cwi.kanbanonline.listeners.OnPostChangeListener
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.services.models.Post
import br.com.cwi.kanbanonline.services.models.Section
import br.com.cwi.kanbanonline.utils.BoardHolder
import br.com.cwi.kanbanonline.utils.UserHolder
import kotlinx.android.synthetic.main.fragment_section_detail.view.*

/**
 * @author eduardo.melzer
 */
class SectionDetailFragment : Fragment() {
    private var mAdapter: PostListAdapter? = null


    companion object {
        fun newInstance(board: Board, section: Section): SectionDetailFragment {
            val fragment = SectionDetailFragment()
            val args = Bundle()
            args.putSerializable("board", board)
            args.putSerializable("section", section)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_section_detail, container, false)

        val board = BoardHolder.board!!
        val serializedSection = arguments?.getSerializable("section") as Section
        val sectionTitle = view!!.sectionTitle
        val boardTitle = view.boardName
        val createdBy = view.boardCreatedBy
        val sectionNumber = view.sectionNumber
        board.sections.forEachIndexed { index, section ->
            if(section.id == serializedSection.id ) {
                sectionTitle.text = "Seção " + section.id
                boardTitle.text = "Board " + board.name
                createdBy.text = "Criado por " + board.createdBy
                sectionNumber.text = (index + 1).toString() + "/" + board.sections.size.toString()
            }
        }

        view.addPost.setOnClickListener {
            val arg = Bundle()
            arg.putString("boardId", board.id)
            arg.putString("sectionId", serializedSection.id)
            val dialog = CreatePostDialogFragment()
            dialog.arguments = arg
            dialog.show(fragmentManager, "CreatePost")
        }

        return view
    }

    fun loadPosts() {
        val user = UserHolder.user!!.email
        val board = BoardHolder.board!!
        val serializedSection = arguments?.getSerializable("section") as Section
        val section = board.sections.find { it -> it.id == serializedSection.id}!!
        val posts: MutableList<Post>

        posts = if (board.createdBy.equals(user)) {
            section.posts
        } else {
            section.posts.asSequence()
                    .filter { user.equals(it.createdBy) }
                    .toMutableList()
        }

        mAdapter = PostListAdapter(posts, context!!) { post ->
            val arg = Bundle()

            arg.putString("description", post.description)
            arg.putString("createdBy", post.createdBy)
            arg.putString("id", post.id)
            val dialog = DetailsPostDialogFragment()
            dialog.arguments = arg
            dialog.show(fragmentManager, "DetailsPost")
        }

        val mLayoutManager = GridLayoutManager(context, 2)
        view!!.postRecyclerView.layoutManager = mLayoutManager
        view!!.postRecyclerView.itemAnimator = DefaultItemAnimator()
        view!!.postRecyclerView.adapter = mAdapter
    }

}