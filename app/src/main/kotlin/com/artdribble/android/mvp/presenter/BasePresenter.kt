package com.artdribble.android.mvp.presenter

interface BasePresenter {
    fun onDestroy()
    fun onPause()
    fun onResume()
    fun onViewCreated()
}