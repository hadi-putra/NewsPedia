package com.example.hadi.newspedia.view

import com.example.hadi.newspedia.data.NewsModel

/**
 * Created by hadi on 31/01/18.
 */
interface ArticlesView {
    fun showArticles(articles: List<NewsModel>)
    fun showLoading()
    fun hideLoading()
    fun showErrorMessage(message: String)
}