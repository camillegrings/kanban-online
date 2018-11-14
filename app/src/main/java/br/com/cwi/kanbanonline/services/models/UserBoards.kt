package br.com.cwi.kanbanonline.services.models

import br.com.cwi.kanbanonline.utils.UserHolder
import br.com.cwi.kanbanonline.utils.toFirestoreUser
import com.google.firebase.firestore.DocumentReference
import java.util.HashMap

class UserBoards {
    var user: User? = null
    var boardId: DocumentReference? = null
    var admin: Boolean = false

    constructor(boardId: DocumentReference) {
        this.user = UserHolder.user?.toFirestoreUser()
        this.boardId = boardId
        this.admin = false
    }

    fun toMap(): Map<String, Any?> {

        val result = HashMap<String, Any?>()
        result["userId"] = user?.id!!
        result["boardId"] = boardId
        result["admin"] = admin

        return result
    }
}