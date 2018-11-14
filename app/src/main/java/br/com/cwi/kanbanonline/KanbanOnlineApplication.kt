package br.com.cwi.kanbanonline

import android.app.Application
import br.com.cwi.kanbanonline.utils.UserHolder
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class KanbanOnlineApplication: Application()  {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        UserHolder.user = FirebaseAuth.getInstance().currentUser
    }
}