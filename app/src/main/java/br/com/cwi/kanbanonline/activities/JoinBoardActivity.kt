package br.com.cwi.kanbanonline.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.presenters.JoinBoardPresenter
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.views.JoinBoardView
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_join_board.*

class JoinBoardActivity : AppCompatActivity(), JoinBoardView {
    private val presenter: JoinBoardPresenter by lazy {
        JoinBoardPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_board)

        FirestoreService.listenerJoinBoard = presenter

        joinBoardToolbar.setNavigationOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        joinBoardButton.setOnClickListener {
            val hashCode = hashBoardEditText.text.toString()
            presenter.findBoard(hashCode)
        }
    }

    override fun onJoinBoardSucceeded(documentSnapshot: DocumentSnapshot) {
        Toast.makeText(applicationContext, getString(R.string.join_board_success), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        //intent.putExtra("board", documentSnapshot.id)
        startActivity(intent)
    }

    override fun onJoinBoardFailed() {
        Toast.makeText(applicationContext, getString(R.string.join_board_fail), Toast.LENGTH_SHORT).show()
    }

}
