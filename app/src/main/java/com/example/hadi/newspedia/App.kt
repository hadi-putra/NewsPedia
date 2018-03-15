package com.example.hadi.newspedia

import com.example.hadi.newspedia.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by hadi on 31/01/18.
 */
class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.builder().create(this)
    }

}