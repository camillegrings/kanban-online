package br.com.cwi.kanbanonline.listeners

import br.com.cwi.kanbanonline.services.models.Board

/**
 * @author eduardo.melzer
 */
interface OnBoardLoadListener {
    fun onBoardLoaded(boards: List<Board>)
    fun onBoardLoadFailed(e: Exception)
}