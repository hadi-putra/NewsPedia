package com.example.hadi.newspedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by hadi on 31/01/18.
 */
fun ViewGroup.inflateLayout(resLayout: Int, attachRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(resLayout, this, attachRoot)
}