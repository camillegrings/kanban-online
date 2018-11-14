package br.com.cwi.kanbanonline.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.presenters.LoginPresenter
import br.com.cwi.kanbanonline.utils.UserHolder
import br.com.cwi.kanbanonline.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author eduardo.melzer
 */
class LoginActivity : AppCompatActivity(), LoginView {

    private val presenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        createAccountLink.setOnClickListener { this.onRegisterClick() }

        googleSignInButton.setOnClickListener {
            presenter.logIn(this)
        }

        logInButton.setOnClickListener {
            presenter.logIn(
                    emailEditText?.text.toString(),
                    passwordEditText?.text.toString())
        }

        if (UserHolder.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LoginPresenter.REQUEST_CODE) {
            presenter.handleLoginResult(data)
        }
    }

    override fun onLoginSucceeded() {
        var display: String? = ""

        UserHolder.user?.run {
            display = if (displayName != null) displayName else email
        }

        Toast.makeText(this, "Olá, $display", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onLoginFailed(reason: String?) {
        Toast.makeText(this,
                reason ?: getString(R.string.try_again_login),
                Toast.LENGTH_LONG).show()
    }

    override fun onLoginFailed() {
        this.onLoginFailed(getString(R.string.try_again_login))
    }

    override fun onRegisterClick() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
