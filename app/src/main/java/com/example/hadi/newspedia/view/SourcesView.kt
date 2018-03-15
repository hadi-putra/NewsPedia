package com.example.hadi.newspedia.view

import com.example.hadi.newspedia.data.SourceModel

/**
 * Created by hadi on 31/01/18.
 */
interface SourcesView {
    fun showSources(sources: List<SourceModel>)
    fun showLoading()
    fun hideLoading()
    fun showErrorMessage(message: String)
}