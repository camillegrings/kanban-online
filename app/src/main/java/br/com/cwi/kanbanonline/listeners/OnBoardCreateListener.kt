package br.com.cwi.kanbanonline.listeners

import br.com.cwi.kanbanonline.services.models.Board

/**
 * @author eduardo.melzer
 */
interface OnBoardCreateListener {
    fun onBoardCreated(board: Board)
    fun onBoardCreateFailed(e: Exception)
}