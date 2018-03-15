package com.example.hadi.newspedia.dagger

import com.example.hadi.newspedia.MainActivity
import com.example.hadi.newspedia.view.SourcesView
import dagger.Binds
import dagger.Module

/**
 * Created by hadi on 31/01/18.
 */
@Module
abstract class SourcesModule{

    @Binds
    internal abstract fun provideSourcesView(activity: MainActivity): SourcesView
}