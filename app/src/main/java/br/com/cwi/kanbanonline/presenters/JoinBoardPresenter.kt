package br.com.cwi.kanbanonline.presenters

import android.util.Log
import br.com.cwi.kanbanonline.listeners.onJoinBoardListener
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.views.JoinBoardView
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception

class JoinBoardPresenter(private val view: JoinBoardView): onJoinBoardListener {
    companion object {
        const val TAG = "JoinBoardPresenter"
    }

    fun findBoard(hashCodeBoard: String){
        FirestoreService.joinBoard(hashCodeBoard)
    }

    override fun onBoardJoin(documentSnapshot: DocumentSnapshot) {
        Log.e(TAG, "Board found with ID: " + documentSnapshot?.id)
        view.onJoinBoardSucceeded(documentSnapshot)
    }

    override fun onBoardJoinFailed(e: Exception) {
        Log.e(TAG, "Error searching board", e)
    }
}