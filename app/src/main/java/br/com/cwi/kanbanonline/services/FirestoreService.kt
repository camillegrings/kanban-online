package br.com.cwi.kanbanonline.services

import br.com.cwi.kanbanonline.listeners.*
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.services.models.Post
import br.com.cwi.kanbanonline.services.models.Section
import br.com.cwi.kanbanonline.utils.BoardHolder
import br.com.cwi.kanbanonline.utils.UserHolder
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

/**
 * @author eduardo.melzer
 */
object FirestoreService {

    private enum class COLLECTION(val value: String) {
        BOARDS("boards"),
        USER_BOARDS("user_boards"),
        POSTS("posts")
    }

    private val firestore: FirebaseFirestore by lazy {
        val firestore = FirebaseFirestore.getInstance()

        val settings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()

        firestore.firestoreSettings = settings

        return@lazy firestore
    }

    var listenerCreateBoard: OnBoardCreateListener? = null
    var listenerJoinBoard: onJoinBoardListener? = null
    var listenerLoadBoard: OnBoardLoadListener? = null
    var listenerFoundBoard: OnBoardFoundListener? = null
    var listenerCreatePost: OnPostChangeListener? = null
    var listenerLoadPost: OnPostLoadListener? = null


    fun findBoard(id: String) {
        firestore.collection(COLLECTION.BOARDS.value)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val board = documentSnapshot.toObject(Board::class.java)
                    listenerFoundBoard?.onBoardFound(board)
                }
                .addOnFailureListener { e ->
                    listenerFoundBoard?.onBoardFoundFailed(e)
                }
    }

    fun joinBoard(id: String) {
        firestore.collection(COLLECTION.BOARDS.value)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val board = documentSnapshot.toObject(Board::class.java)!!
                    UserHolder.user!!.email?.let { email ->
                        if (board.users.none { it == email }) {
                            board.users.add(email)

                            documentSnapshot.reference.set(board.toMap())
                                    .addOnCompleteListener {
                                        BoardHolder.board = board
                                        listenerJoinBoard?.onBoardJoin(documentSnapshot)
                                    }
                                    .addOnFailureListener {
                                        listenerJoinBoard?.onBoardJoinFailed(it)
                                    }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    listenerJoinBoard?.onBoardJoinFailed(e)
                }
    }

    fun readBoards() {
        firestore.collection(COLLECTION.BOARDS.value)
                .whereArrayContains("users", UserHolder.user?.email!!)
                .get()
                .addOnCompleteListener { task ->
                    val boardList = mutableListOf<Board>()

                    task.result?.forEach {
                        val board = it!!.toObject(Board::class.java)
                        board.id = it.id
                        boardList.add(board)
                    }

                    listenerLoadBoard?.onBoardLoaded(boardList)
                }
    }

    fun readPosts(boardId: String) {
        firestore.collection(COLLECTION.POSTS.value)
                .whereEqualTo("boardId", boardId)
                .get()
                .addOnCompleteListener { task ->
                    val postList = mutableListOf<Post>()

                    task.result?.forEach {
                        val post = it!!.toObject(Post::class.java)
                        post.id = it.id
                        postList.add(post)
                    }

                    listenerLoadPost?.onPostLoaded(postList)
                }
    }

    fun createBoard(name: String, anonymous: Boolean, sections: List<String>, expirationDate: Timestamp?) {
        val board = Board(name, anonymous, sections.asSequence().map { Section(it) }.toMutableList(), expirationDate)

        firestore.collection(COLLECTION.BOARDS.value)
                .add(board.toMap())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        board.id = task.result?.id
                        BoardHolder.board = board
                        listenerCreateBoard?.onBoardCreated(board)
                    } else {
                        listenerCreateBoard?.onBoardCreateFailed(task.exception!!)
                    }
                }
    }

    fun createPost(description: String, boardId: String, sectionId: String) {
        val post = Post(description, boardId, sectionId)

        firestore.collection(COLLECTION.POSTS.value)
                .add(post.toMap())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        post.id = task.result?.id
                        val section = BoardHolder.board?.sections?.find { it -> it.id == sectionId }
                        section?.posts?.add(post)
                        listenerCreatePost?.onPostChangeSucceded()
                    } else {
                        listenerCreatePost?.onPostChangeFailed(task.exception!!)
                    }
                }
    }

    fun editPost(description: String, idBoard: String, section: Int, idPost: String) {

        firestore.collection(COLLECTION.BOARDS.value)
                .document(idBoard)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val board = documentSnapshot.toObject(Board::class.java)!!
                    board.sections[section].posts.forEach {
                        if(it.id == idPost){
                            it.description = description
                        }
                    }

                    documentSnapshot.reference.update(board.toMap())
                            .addOnSuccessListener {
                                listenerCreatePost?.onPostChangeSucceded()
                            }
                            .addOnFailureListener {
                                listenerCreatePost?.onPostChangeFailed(it)
                            }
                }
                .addOnFailureListener{
                    listenerCreatePost?.onPostChangeFailed(it)
                }

    }

    fun deletePost(postId: String) {
        firestore.collection(COLLECTION.POSTS.value)
                .document(postId)
                .delete()
    }
}