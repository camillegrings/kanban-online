package br.com.cwi.kanbanonline.services.models

import java.io.Serializable
import java.util.HashMap

/**
 * @author eduardo.melzer
 */
class Section : Serializable {
    var id: String? = null
    var description: String? = null
    var posts: MutableList<Post> = mutableListOf()

    constructor() {}

    constructor(id: String) {
        this.id = id
    }

    fun toMap(): Map<String, Any?> {

        val result = HashMap<String, Any?>()
        result["id"] = id!!
        result["description"] = description
        result["posts"] = posts
        return result
    }
}