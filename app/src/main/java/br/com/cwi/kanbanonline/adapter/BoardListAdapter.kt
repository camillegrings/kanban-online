package br.com.cwi.kanbanonline.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.utils.BoardHolder
import br.com.cwi.kanbanonline.utils.toSimpleString
import kotlinx.android.synthetic.main.board_list_item.view.*

/**
 * @author eduardo.melzer
 */
class BoardListAdapter(
        private val boards: List<Board>,
        private val context: Context,
        private val onClick: (board: Board) -> Unit) : Adapter<BoardListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val board = boards[position]
        holder.bindView(board, context)

        holder.itemView.setOnClickListener {
            BoardHolder.board = board
            onClick(board)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.board_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return boards.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(board: Board, context: Context) {
            val title = itemView.boardName
            val createdAt = itemView.createdAt
            val image = itemView.imagePostBoard

            title.text = board.name
            createdAt.text = context.getString(R.string.created_at, board.createdAt?.toDate()?.toSimpleString())
            image.setImageResource(R.drawable.ic_post_it)
        }

    }
}