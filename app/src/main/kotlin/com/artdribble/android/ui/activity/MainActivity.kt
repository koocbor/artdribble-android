package com.artdribble.android.ui.activity

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.GestureDetector
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.artdribble.android.ArtDribbleApp

import com.artdribble.android.R
import com.artdribble.android.models.ArtsyArtist
import com.artdribble.android.models.ArtsyArtistInfo
import com.artdribble.android.models.ArtsyArtwork
import com.artdribble.android.mvp.presenter.ArtworkPresenter
import com.artdribble.android.mvp.view.ArtworkView
import com.artdribble.android.utils.loadUrl
import com.artdribble.android.widget.ArtistInfoView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : BaseActivity(),
        ArtworkView {

    val INITIAL_HIDE_DELAY = 1000L

    lateinit var decoreView: View

    var blurred: Boolean = false

    val hideSystemUiHandler: Handler = object: Handler() {
        override fun handleMessage(msg: Message?) {
            hideSystemUI()
        }
    }

    lateinit var clickDetector: GestureDetector

    @Inject
    protected lateinit var presenter: ArtworkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArtDribbleApp.appComponent.inject(this)

        setContentView(R.layout.activity_main)

        initClickDetector()
        initViews()
        initActionBar()

        presenter.setView(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        delayedHide(INITIAL_HIDE_DELAY)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main_activity, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadDailyArtwork()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            delayedHide(INITIAL_HIDE_DELAY)
        } else {
            hideSystemUiHandler.removeMessages(0)
        }
    }

    private fun bindArtistInfo(info: ArtsyArtistInfo?) {
        if (info?.embedded?.artists == null || info.embedded.artists.isEmpty()) {
            return;
        }
    }

    private fun delayedHide(delayMillis: Long) {
        hideSystemUiHandler.removeMessages(0)
        hideSystemUiHandler.sendEmptyMessageDelayed(0, delayMillis)
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decoreView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    xor View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    xor View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    xor View.SYSTEM_UI_FLAG_FULLSCREEN
                    xor View.SYSTEM_UI_FLAG_IMMERSIVE
            )
        }

        if (blurred) {
            val fadeOut: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            fadeOut.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation?) {
                    art_info_container.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationStart(animation: Animation?) {

                }
            })
            art_info_container.startAnimation(fadeOut)
        }
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowHomeEnabled(false)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun initClickDetector() {
        clickDetector  = object: GestureDetector(this, object: GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {

                var visible: Boolean = (decoreView.systemUiVisibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0
                if (visible) {
                    hideSystemUI()
                } else {
                    showSystemUI()
                }
                return true;
            }
        }) {}
    }

    private fun initViews() {

        art_info_container.setClickable(true)
        art_info_container.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return clickDetector.onTouchEvent(event)
            }
        })

        art_image.setClickable(true)
        art_image.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return clickDetector.onTouchEvent(event)
            }
        })

        decoreView = window.decorView
    }

    private fun showSystemUI() {
        decoreView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            xor View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            xor View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )

        val fadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        art_info_container.startAnimation(fadeIn)
        art_info_container.visibility = View.VISIBLE;

        blurred = true
    }

    override fun displayArtistInfo(info: List<ArtsyArtist>?) {
        artist_names_container.removeAllViews()

        if (info == null) {
            artist_names_container.visibility = View.GONE
            return;
        }

        for (artist: ArtsyArtist in info) {
            var artistView: ArtistInfoView = ArtistInfoView(this)
            artistView.displayArtist(artist)
            artist_names_container.addView(artistView)
        }
    }

    override fun displayArtworkInfo(info: ArtsyArtwork) {
        toolbar_title.text = info.title
        artwork_date.text = info.date
        artwork_medium.text = info.medium
        collecting_institution.text = info.collecting_institution
    }

    override fun displayImage(url: String?) {
        art_image.loadUrl(url, object: RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                if (resource != null) {
                    Handler().postDelayed( {
                        Blurry.with(this@MainActivity)
                            .radius(10)
                            .sampling(8)
                            .color(Color.argb(66,255,255,255))
                            .capture(art_image)
                            .into(art_image_blurry) }, 500)
                }
                return false;
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                Log.e("DRIBBLE", "Error", e)
                return false;
            }
        })
    }

    override fun toggleProgress(show: Boolean) {

    }
    // endregion
}
