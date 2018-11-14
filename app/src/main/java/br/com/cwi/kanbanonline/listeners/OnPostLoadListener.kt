package br.com.cwi.kanbanonline.listeners

import br.com.cwi.kanbanonline.services.models.Post

/**
 * @author eduardo.melzer
 */
interface OnPostLoadListener {
    fun onPostLoaded(posts: List<Post>)
}