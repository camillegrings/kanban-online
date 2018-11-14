package br.com.cwi.kanbanonline.listeners

import br.com.cwi.kanbanonline.services.models.Board

interface OnBoardFoundListener {
    fun onBoardFound(board: Board?)
    fun onBoardFoundFailed(e: Exception)
}