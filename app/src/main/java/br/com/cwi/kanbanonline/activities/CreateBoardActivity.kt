package br.com.cwi.kanbanonline.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.presenters.CreateBoardPresenter
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.views.CreateBoardView
import kotlinx.android.synthetic.main.activity_create_board.*

class CreateBoardActivity : AppCompatActivity(), CreateBoardView {

    val sections: MutableList<TextInputLayout> = mutableListOf()

    private val presenter: CreateBoardPresenter by lazy {
        CreateBoardPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)

        FirestoreService.listenerCreateBoard = presenter

        saveBoardButton.setOnClickListener {
            // TODO adicionar loader visual
            presenter.saveBoard(this)
        }

        createBoardToolbar.setNavigationOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // TODO Refatorar para utilizar uma inflatedList
        val numberSections = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        numberSectionsSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, numberSections)
        numberSectionsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val newQtdSections = position + 1
                val oldQtdSections = nameSectionLayout.childCount

                if (oldQtdSections > newQtdSections) {
                    for (i in oldQtdSections-1 downTo newQtdSections) {
                        sections.remove(nameSectionLayout.getChildAt(i))
                        nameSectionLayout.removeViewAt(i)
                    }
                } else if (oldQtdSections < newQtdSections) {

                    for (i in oldQtdSections until newQtdSections) {
                        val inputLayout = TextInputLayout(this@CreateBoardActivity)
                        val editText = TextInputEditText(this@CreateBoardActivity)

                        editText.setHint("Nome da sessão ${i + 1}")

                        inputLayout.addView(editText)
                        sections.add(inputLayout)

                        nameSectionLayout.addView(inputLayout, i)
                    }
                }
            }

        }
    }

    override fun onCreateBoardSucceeded(board: Board) {
        Toast.makeText(applicationContext, getString(R.string.board_create_success), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, BoardActivity::class.java)
        intent.putExtra("board", board)

        startActivity(intent)
    }

    override fun onCreateBoardFailed() {
        Toast.makeText(applicationContext, getString(R.string.board_create_fail), Toast.LENGTH_SHORT).show()
    }

}
