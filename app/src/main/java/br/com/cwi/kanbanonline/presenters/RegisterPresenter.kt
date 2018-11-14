package br.com.cwi.kanbanonline.presenters

import br.com.cwi.kanbanonline.utils.UserHolder
import br.com.cwi.kanbanonline.views.RegisterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterPresenter(private val view: RegisterView) {

    companion object {
        const val REQUEST_CODE = 8000
        const val TAG = "Kanban.RegisterPresenter"
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun register(email: String, password: String, name:String) {
        if (!email.isBlank() && !password.isBlank()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                            profileUpdates.setDisplayName(name)
                            firebaseAuth.currentUser?.updateProfile(profileUpdates.build())
                                    ?.addOnCompleteListener {
                                        UserHolder.user = firebaseAuth.currentUser
                                        view.onRegisterSucceeded()
                                    }
                        } else {
                            view.onRegisterFailed(it.exception?.localizedMessage)
                        }
                    }

        } else {
            view.onRegisterFailed()
        }
    }
}