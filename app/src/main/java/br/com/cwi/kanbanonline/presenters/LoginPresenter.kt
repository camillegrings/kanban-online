package br.com.cwi.kanbanonline.presenters

import android.app.Activity
import android.content.Intent
import android.util.Log
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.utils.UserHolder
import br.com.cwi.kanbanonline.views.LoginView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * @author eduardo.melzer
 */
class LoginPresenter(private val view: LoginView) {

    companion object {
        const val REQUEST_CODE = 7000
        const val TAG = "Kanban.LoginPresenter"
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun logIn(activity: Activity) {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .build()

        UserHolder.signInOptions = options
        val client = GoogleSignIn.getClient(activity, options)
        activity.startActivityForResult(client.signInIntent, REQUEST_CODE)
    }

    fun logIn(email: String, password: String) {
        if (!email.isBlank() && !password.isBlank()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            UserHolder.user = firebaseAuth.currentUser
                            view.onLoginSucceeded()
                        } else {
                            view.onLoginFailed(it.exception?.localizedMessage)
                        }
                    }
        } else {
            view.onLoginFailed()
        }
    }

    fun handleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (task.isSuccessful) {
                            UserHolder.user = firebaseAuth.currentUser
                            view.onLoginSucceeded()
                        } else {
                            Log.e(TAG, "handleLoginResult@signInWithCredential: " + task.exception?.localizedMessage, task.exception)
                            view.onLoginFailed()
                        }
                    }

        } catch (exception: ApiException) {
            Log.e(TAG, "handleLoginResult: " + exception.localizedMessage, exception)
            view.onLoginFailed()
        }
    }
}