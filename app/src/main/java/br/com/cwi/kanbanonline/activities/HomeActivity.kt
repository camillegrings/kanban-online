package br.com.cwi.kanbanonline.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.cwi.kanbanonline.R
import br.com.cwi.kanbanonline.adapter.BoardListAdapter
import br.com.cwi.kanbanonline.listeners.OnBoardFoundListener
import br.com.cwi.kanbanonline.listeners.OnBoardLoadListener
import br.com.cwi.kanbanonline.listeners.OnPostLoadListener
import br.com.cwi.kanbanonline.presenters.HomePresenter
import br.com.cwi.kanbanonline.services.FirestoreService
import br.com.cwi.kanbanonline.services.models.Board
import br.com.cwi.kanbanonline.services.models.Post
import br.com.cwi.kanbanonline.utils.BoardHolder
import br.com.cwi.kanbanonline.utils.UserHolder
import br.com.cwi.kanbanonline.utils.loadImage
import br.com.cwi.kanbanonline.views.HomeView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HomeView, OnBoardLoadListener {

    private var mAdapter: BoardListAdapter? = null

    private val presenter: HomePresenter by lazy {
        HomePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        FirestoreService.listenerLoadBoard = this

        presenter.loadBoards(this)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        addCurrentUserInformations()

        logoutButton.setOnClickListener {
            UserHolder.logOut(this)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent = Intent()

        when (item.itemId) {
            R.id.nav_new_board -> {
                intent = Intent(this, CreateBoardActivity::class.java)
            }
            R.id.nav_join_board -> {
                intent = Intent(this, JoinBoardActivity::class.java)
            }
            R.id.nav_ferramentas -> {

            }
            R.id.nav_sobre -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        startActivity(intent)
        return true
    }

    override fun onBoardLoaded(boards: List<Board>) {
        Toast.makeText(applicationContext, getString(R.string.board_loaded), Toast.LENGTH_SHORT).show()

        mAdapter = BoardListAdapter(boards, applicationContext) { board ->
            Toast.makeText(applicationContext, "Clicou no board " + board.id, Toast.LENGTH_SHORT).show()

            BoardHolder.board = board
            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("board", board)
            startActivity(intent)
        }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        myBoards.layoutManager = mLayoutManager
        myBoards.itemAnimator = DefaultItemAnimator()
        myBoards.adapter = mAdapter
    }

    override fun onBoardLoadFailed(e: Exception) {
        TODO("not implemented")
    }

    private fun addCurrentUserInformations() {
        val name = UserHolder.user?.displayName
        val email = UserHolder.user?.email
        val image = UserHolder.user?.photoUrl //Uri.parse("https://icon-icons.com/icons2/1371/PNG/512/batman_90804.png") //UserHolder.user?.photoUrl

        nav_view.getHeaderView(0).let {
            it.userName.text = name
            it.userEmail.text = email
            image?.run {
                it.userProfileImage.loadImage(toString())
            }
        }
    }
}


