package com.example.hadi.newspedia.dagger

import com.example.hadi.newspedia.ArticlesActivity
import com.example.hadi.newspedia.view.ArticlesView
import dagger.Binds
import dagger.Module

/**
 * Created by hadi on 31/01/18.
 */
@Module
abstract class ArticlesModule{

    @Binds
    internal abstract fun provideArticlesView(activity: ArticlesActivity): ArticlesView
}