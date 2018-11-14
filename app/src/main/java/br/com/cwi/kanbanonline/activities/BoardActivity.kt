package br.com.cwi.kanbanonline.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.adapter.SectionPagerAdapter
import br.com.cwi.kanbanonline.fragments.dialogs.ShareKeyOfBoardDialogFragment
import br.com.cwi.kanbanonline.listeners.OnBoardFoundListener
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.utils.BoardHolder
import br.com.cwi.kanbanonline.utils.UserHolder
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : OnBoardFoundListener, FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        val board = BoardHolder.board!!

        val fragmentPagerAdapter =
                SectionPagerAdapter(supportFragmentManager, this, board)

        mainViewPager.offscreenPageLimit = board.sections.size
        mainViewPager.adapter = fragmentPagerAdapter

//        tabLayout.setupWithViewPager(mainViewPager)

        boardToolbar.setNavigationOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        shareBoardBtn.setOnClickListener {
            val dialog = ShareKeyOfBoardDialogFragment()
            dialog.show(supportFragmentManager, "ShareKeyOfBoard")
        }

        isAdminLoadingBoard(board)
        addCurrentUserInformation(board)

    }

    override fun onBoardFound(board: Board?) {
        Toast.makeText(applicationContext, "asdas", Toast.LENGTH_SHORT).show()
    }

    override fun onBoardFoundFailed(e: Exception) {
        Toast.makeText(applicationContext, getString(R.string.board_not_found), Toast.LENGTH_SHORT).show()
    }

    private fun addCurrentUserInformation(board: Board) {
        val nameBoard = board.name

        boardTitle.text = nameBoard
    }

    private fun isAdminLoadingBoard(board: Board) {
        if (board.createdBy == UserHolder.user?.email) {
            shareBoardBtn.visibility = View.VISIBLE
        } else {
            shareBoardBtn.visibility = View.INVISIBLE
        }
    }
}
