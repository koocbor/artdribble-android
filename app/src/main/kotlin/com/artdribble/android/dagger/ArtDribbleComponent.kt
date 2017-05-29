package com.artdribble.android.dagger

import com.artdribble.android.ArtDribbleApp
import com.artdribble.android.ui.activity.BaseActivity

/**
 * Created by robcook on 5/29/17.
 */
interface ArtDribbleComponent {

    fun inject(app: ArtDribbleApp): ArtDribbleApp

    fun inject(baseActivity: BaseActivity): BaseActivity

}