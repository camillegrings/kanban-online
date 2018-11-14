package br.com.cwi.kanbanonline.services.models

import br.com.cwi.kanbanonline.utils.UserHolder
import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*

/**
 * @author eduardo.melzer
 */
class Board : Serializable {

    var id: String? = null
    var name: String? = null
    var anonymous: Boolean = true
    @Transient var createdAt: Timestamp? = null
    @Transient var expirationDate: Timestamp? = null
    var createdBy: String? = null
    var sections: MutableList<Section> = mutableListOf()
    var users: MutableList<String> = mutableListOf()

    constructor() {}

    constructor(name: String, anonymous: Boolean, sections: MutableList<Section>, expirationDate: Timestamp? = null) {
        this.name = name
        this.anonymous = anonymous
        this.expirationDate = expirationDate
        this.sections = sections

        createdAt = Timestamp.now()
        createdBy = UserHolder.user?.email
        users = mutableListOf(createdBy!!)
    }

    constructor(id: String, name: String, anonymous: Boolean, createdAt: Timestamp, expirationDate: Timestamp?, createdBy: String) {
        this.id = id
        this.name = name
        this.anonymous = anonymous
        this.createdAt = createdAt
        this.expirationDate = expirationDate
        this.createdBy = createdBy
        this.users = mutableListOf(createdBy)
    }

    fun toMap(): Map<String, Any?> {

        val result = HashMap<String, Any?>()
        result["name"] = name!!
        result["anonymous"] = anonymous
        result["createdAt"] = createdAt
        result["expirationDate"] = expirationDate
        result["createdBy"] = createdBy
        result["users"] = users
        result["sections"] = sections.map {
            it.toMap()
        }

        return result
    }
}