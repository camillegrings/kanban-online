package br.com.cwi.kanbanonline.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.presenters.RegisterPresenter
import br.com.cwi.kanbanonline.utils.UserHolder
import br.com.cwi.kanbanonline.views.RegisterView
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterView {

    private val presenter: RegisterPresenter by lazy {
        RegisterPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginLink.setOnClickListener { this.onLoginClick() }

        registerButton.setOnClickListener {
            presenter.register(
                    emailEditText?.text.toString(),
                    passwordEditText?.text.toString(),
                    nameEditText.text.toString())
        }
    }

    override fun onRegisterSucceeded() {
        val display: String? = UserHolder.user?.displayName

        Toast.makeText(this, "Olá, $display", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onRegisterFailed() {
        this.onRegisterFailed(getString(R.string.try_again_login))
    }

    override fun onRegisterFailed(reason: String?) {
        Toast.makeText(this,
                reason ?: getString(R.string.try_again_login),
                Toast.LENGTH_LONG).show()
    }


    override fun onLoginClick() {
        startActivity(Intent(this, LoginActivity::class.java))
    }


}
