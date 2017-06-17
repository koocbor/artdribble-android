package com.artdribble.android.dagger

import com.artdribble.android.ArtDribbleApp
import com.artdribble.android.services.ArtDribbleFirebaseGetDailyDribbleService
import com.artdribble.android.services.ArtDribbleJobSchedulerGetDailyDribbleService
import com.artdribble.android.ui.activity.BaseActivity
import com.artdribble.android.ui.activity.MainActivity

/**
 * Created by robcook on 5/29/17.
 */
interface ArtDribbleComponent {

    fun inject(app: ArtDribbleApp): ArtDribbleApp

    fun inject(artDribbleFirebaseGetDailyDribbleService: ArtDribbleFirebaseGetDailyDribbleService): ArtDribbleFirebaseGetDailyDribbleService

    fun inject(artDribbleJobSchedulerGetDailyDribbleService: ArtDribbleJobSchedulerGetDailyDribbleService): ArtDribbleJobSchedulerGetDailyDribbleService

    fun inject(baseActivity: BaseActivity): BaseActivity

    fun inject(mainActivity: MainActivity): MainActivity

}