package br.com.cwi.kanbanonline.listeners

interface OnPostChangeListener {
    fun onPostChangeSucceded()
    fun onPostChangeFailed(e: Exception)
}