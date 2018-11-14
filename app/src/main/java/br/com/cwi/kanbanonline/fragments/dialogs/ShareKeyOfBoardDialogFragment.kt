package br.com.cwi.kanbanonline.fragments.dialogs

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.utils.BoardHolder
import kotlinx.android.synthetic.main.dialog_share_key_board.*
import kotlinx.android.synthetic.main.dialog_share_key_board.view.*

class ShareKeyOfBoardDialogFragment : DialogFragment() {

    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_share_key_board, container, false)
        myClipboard = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        BoardHolder.board?.let {
            view.keyOfBoard.text = it.id
        }

        view.closeShareBoard.setOnClickListener {
            dialog.dismiss()
        }

        view.btnCopyId.setOnClickListener {
            myClip = ClipData.newPlainText("text", keyOfBoard.text)
            myClipboard?.primaryClip = myClip!!

            Toast.makeText(context, getString(R.string.clipboard_copied), Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }

        return view
    }

}