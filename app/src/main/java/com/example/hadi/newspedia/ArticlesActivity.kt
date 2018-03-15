package com.example.hadi.newspedia

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.hadi.newspedia.adapter.ArticleAdapter
import com.example.hadi.newspedia.data.NewsModel
import com.example.hadi.newspedia.data.SourceModel
import com.example.hadi.newspedia.presenter.ArticlesPresenter
import com.example.hadi.newspedia.view.ArticlesView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ArticlesActivity : AppCompatActivity(), ArticlesView, SearchView.OnQueryTextListener {
    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        clearState()
        presenter.query = query
        presenter.loadArticlesBySource()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun showArticles(articles: List<NewsModel>) {
        adapter.setArticles(articles)
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
        list.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.INVISIBLE
        list.visibility = View.VISIBLE
    }

    @Inject lateinit var presenter: ArticlesPresenter
    private lateinit var adapter: ArticleAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        list.layoutManager = layoutManager
        list.setHasFixedSize(true)

        adapter = ArticleAdapter()
        list.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.loadArticlesBySource(page+1)
            }
        }
        list.addOnScrollListener(scrollListener)

        if (intent.hasExtra("domain")){
            val source = intent.getParcelableExtra<SourceModel>("domain")
            presenter.setArticle(source)
            title = source.name
            presenter.loadArticlesBySource()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(p0: View?) {
                clearState()
                presenter.query = null
                presenter.loadArticlesBySource()
            }

            override fun onViewAttachedToWindow(p0: View?) {

            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        presenter.clear()
        super.onDestroy()
    }

    fun clearState(){
        adapter.clearState()
        scrollListener.resetState()
    }
}
