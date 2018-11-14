package br.com.cwi.kanbanonline.presenters

import android.util.Log
import br.com.cwi.kanbanonline.activities.CreateBoardActivity
import br.com.cwi.kanbanonline.listeners.OnBoardCreateListener
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.views.CreateBoardView
import kotlinx.android.synthetic.main.activity_create_board.*

/**
 * @author eduardo.melzer
 */
class CreateBoardPresenter(private val view: CreateBoardView) : OnBoardCreateListener {

    companion object {
        const val TAG = "CreateBoardPresenter"
    }

    override fun onBoardCreated(board: Board) {
        Log.e(TAG, "Board created with ID: " + board.id)
        view.onCreateBoardSucceeded(board)
    }

    override fun onBoardCreateFailed(e: Exception) {
        Log.e(TAG, "Error creating board", e)
        view.onCreateBoardFailed()
    }

    // TODO adicionar "Data de expiração" na tela de criação do board
    fun saveBoard(activity: CreateBoardActivity) {
        val boardName = activity.nameBoardEditText.text.toString()
        val sections = activity.sections.map { it.editText?.text.toString() }
        val anonymous = activity.anonBoardCheck.isChecked

        FirestoreService.createBoard(boardName, anonymous, sections, null)
    }
}