package br.com.cwi.kanbanonline.views

import br.com.cwi.kanbanonline.services.models.Board
import com.google.firebase.firestore.DocumentSnapshot

/**
 * @author eduardo.melzer
 */
interface LoginView {
    fun onLoginSucceeded()
    fun onLoginFailed()
    fun onLoginFailed(reason: String?)
    fun onRegisterClick()
}

interface RegisterView {
    fun onRegisterSucceeded()
    fun onRegisterFailed()
    fun onRegisterFailed(reason: String?)
    fun onLoginClick()
}

interface CreateBoardView {
    fun onCreateBoardSucceeded(board: Board)
    fun onCreateBoardFailed()
}

interface HomeView { }

interface JoinBoardView {
    fun onJoinBoardSucceeded(documentSnapshot: DocumentSnapshot)
    fun onJoinBoardFailed()
}