package br.com.cwi.kanbanonline.listeners

import com.google.firebase.firestore.DocumentSnapshot
import java.lang.Exception

interface onJoinBoardListener {
    fun onBoardJoin(documentSnapshot: DocumentSnapshot)
    fun onBoardJoinFailed(e: Exception)
}