package br.com.cwi.kanbanonline.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.cwi.kanbanonline.fragments.SectionDetailFragment
import br.com.cwi.kanbanonline.listeners.OnPostChangeListener
import br.com.cwi.kanbanonline.listeners.OnPostLoadListener
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.services.models.Post
import br.com.cwi.kanbanonline.utils.BoardHolder
import br.com.cwi.kanbanonline.utils.UserHolder

/**
 * @author eduardo.melzer
 */
class SectionPagerAdapter(
        fm: FragmentManager,
        val context: Context,
        val board: Board) : FragmentPagerAdapter(fm), OnPostLoadListener {

    init {
        FirestoreService.listenerLoadPost = this
        FirestoreService.readPosts(board.id!!)
    }


    private val sectionFragments: MutableList<SectionDetailFragment> by lazy {
        board.sections.asSequence().map {
            SectionDetailFragment.newInstance(board, it)
        }.toMutableList()
    }

    override fun getItem(position: Int): Fragment {
        return sectionFragments[position]
    }

    override fun getCount() = board.sections.size

    override fun getPageTitle(position: Int): CharSequence? {
        return board.sections[position].id
    }


    override fun onPostLoaded(posts: List<Post>) {
        val user = UserHolder.user!!.email

        BoardHolder.board?.let { board ->
            board.sections.forEach {section ->
                section.posts = posts.asSequence().filter { post ->
                    val isFromSameSection = post.sectionId == section.id
                    val isBoardAdmin = board.createdBy == user
                    val isFromSameUser = post.createdBy == user
                    isFromSameSection && (isBoardAdmin || isFromSameUser)
                }.toMutableList()
            }


            sectionFragments.forEach { fragment ->
                fragment.loadPosts()
            }
        }
    }

}