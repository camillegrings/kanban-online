package br.com.cwi.kanbanonline.utils

import android.widget.ImageView
import br.com.cwi.kanbanonline.services.models.User
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.util.*

/**
 * @author eduardo.melzer
 */

fun ImageView.loadImage(url: String?) {
    url?.let {
        Picasso.get()
                .load(it)
                .into(this)
    }
}

fun FirebaseUser.toFirestoreUser(): User {
    return User(this.email!!, this.displayName ?: this.email!!)
}

fun Date.toSimpleString() : String {
    val format = DateFormat.getDateTimeInstance()
    return format.format(this)
}