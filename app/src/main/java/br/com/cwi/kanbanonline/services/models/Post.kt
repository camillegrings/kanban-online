package br.com.cwi.kanbanonline.services.models

import br.com.cwi.kanbanonline.utils.UserHolder
import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*

class Post : Serializable {
    var id: String? = null
    var description: String? = null
    var createdBy: String? = null
    var boardId: String? = null
    var sectionId: String? = null
    @Transient var createdAt: Timestamp? = null

    constructor() {}

    constructor(description: String, boardId: String, sectionId: String) {
        this.id = Random().toString()
        this.description = description
        this.boardId = boardId
        this.sectionId = sectionId
        createdBy = UserHolder.user?.email
        createdAt = Timestamp.now()
    }

    fun toMap(): Map<String, Any?> {

        val result = HashMap<String, Any?>()
        result["id"] = id!!
        result["description"] = description
        result["createdBy"] = createdBy
        result["boardId"] = boardId
        result["sectionId"] = sectionId
        result["createdAt"] = createdAt
        return result
    }
}