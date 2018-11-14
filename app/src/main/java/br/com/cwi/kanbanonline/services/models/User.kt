package br.com.cwi.kanbanonline.services.models


import java.util.HashMap

/**
 * @author eduardo.melzer
 */
class User(id: String, name: String){

    var id: String? = id
    var name: String? = name
    var online: Boolean = false

    fun toMap(): Map<String, Any?> {

        val result = HashMap<String, Any?>()
        result["id"] = id!!
        result["name"] = name!!
        result["online"] = online

        return result
    }

}