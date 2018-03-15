package com.example.hadi.newspedia.dagger

import com.example.hadi.newspedia.ArticlesActivity
import com.example.hadi.newspedia.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by hadi on 31/01/18.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SourcesModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ArticlesModule::class])
    internal abstract fun bindArticlesActivity(): ArticlesActivity
}