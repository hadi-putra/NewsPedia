package com.example.hadi.newspedia

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.hadi.newspedia.adapter.SourceAdapter
import com.example.hadi.newspedia.data.SourceModel
import com.example.hadi.newspedia.presenter.SourcesPresenter
import com.example.hadi.newspedia.view.SourcesView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SourcesView {

    @Inject lateinit var presenter: SourcesPresenter
    private lateinit var adapter: SourceAdapter

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        loading.visibility = View.INVISIBLE
        list.visibility = View.VISIBLE
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
        list.visibility = View.INVISIBLE
    }

    override fun showSources(sources: List<SourceModel>) {
        adapter.setSources(sources)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        list.layoutManager = layoutManager
        list.setHasFixedSize(true)

        adapter = SourceAdapter()
        list.adapter = adapter

        title = "Sources"

        presenter.loadSources()
    }

    override fun onDestroy() {
        presenter.clear()
        super.onDestroy()
    }
}
