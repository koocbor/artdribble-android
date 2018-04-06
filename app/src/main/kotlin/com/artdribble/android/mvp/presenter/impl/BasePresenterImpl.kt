package com.artdribble.android.mvp.presenter.impl

import com.artdribble.android.app.ArtDribbleApp
import com.artdribble.android.mvp.presenter.BasePresenter
import com.artdribble.android.mvp.view.BaseView
import com.artdribble.android.utils.errorMsg
import io.reactivex.disposables.CompositeDisposable

open class BasePresenterImpl : BasePresenter {

    var disposables = CompositeDisposable()

    override fun onDestroy() {
        disposables.dispose()
    }

    override fun onPause() { }

    override fun onResume() { }

    override fun onViewCreated() { }

    protected fun onApiError(throwable: Throwable?, baseView: BaseView?, show: Boolean) {
        ArtDribbleApp.logThrowable(throwable)
        baseView?.let {
            it.toggleProgress(false)
            if (show) {
                //it.showMessage(throwable.errorMsg(it.getBaseActivity()))
            }
        }
    }
}