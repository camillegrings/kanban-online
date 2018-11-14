package br.com.cwi.kanbanonline.presenters

import br.com.cwi.kanbanonline.activities.HomeActivity
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.views.HomeView

/**
 * @author eduardo.melzer
 */
class HomePresenter(private val view: HomeView) {

    companion object {
        const val TAG = "CreateBoardPresenter"
    }

    fun loadBoards(activity: HomeActivity) {
        FirestoreService.readBoards()
    }

}