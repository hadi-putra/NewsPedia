package com.example.hadi.newspedia.presenter

import com.example.hadi.newspedia.NewsApi
import com.example.hadi.newspedia.data.SourceModel
import com.example.hadi.newspedia.view.SourcesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by hadi on 31/01/18.
 */
class SourcesPresenter @Inject
constructor(private val view: SourcesView, private val api: NewsApi) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSources(){
        view.showLoading()
        compositeDisposable.clear()
        compositeDisposable.add(api.getSources()
                .map { it.sources }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({sources: List<SourceModel> ->
                    view.hideLoading()
                    view.showSources(sources)
                }, {throwable: Throwable ->
                    throwable.printStackTrace()
                    view.hideLoading()
                    view.showErrorMessage(throwable.localizedMessage)
                }))
    }

    fun clear() {
        view.hideLoading()
        compositeDisposable.clear()
    }
}