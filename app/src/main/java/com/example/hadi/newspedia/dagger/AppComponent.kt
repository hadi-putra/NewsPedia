package com.example.hadi.newspedia.dagger

import com.example.hadi.newspedia.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by hadi on 31/01/18.
 */
@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<App>{
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<App>()
}