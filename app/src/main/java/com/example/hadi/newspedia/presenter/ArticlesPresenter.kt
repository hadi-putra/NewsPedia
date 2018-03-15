package com.example.hadi.newspedia.presenter

import com.example.hadi.newspedia.NewsApi
import com.example.hadi.newspedia.data.NewsModel
import com.example.hadi.newspedia.data.SourceModel
import com.example.hadi.newspedia.view.ArticlesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by hadi on 31/01/18.
 */
class ArticlesPresenter @Inject
constructor(private val view: ArticlesView, private val api: NewsApi){
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var article: SourceModel? = null
    var query: String? = null

    fun loadArticlesBySource(page: Int = 1){
        if (page == 1)
            view.showLoading()

        compositeDisposable.clear()
        compositeDisposable.add(api.getNews(article!!.domain, query, page)
                .map { it.articles }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({sources: List<NewsModel> ->
                    if (page == 1){
                        view.hideLoading()
                    }
                    view.showArticles(sources)
                }, {throwable: Throwable ->
                    throwable.printStackTrace()
                    if (page == 1){
                        view.hideLoading()
                    }
                    view.showErrorMessage(throwable.localizedMessage)
                }))
    }

    fun setArticle(article: SourceModel){
        this.article = article
    }

    fun clear() {
        view.hideLoading()
        compositeDisposable.clear()
    }
}