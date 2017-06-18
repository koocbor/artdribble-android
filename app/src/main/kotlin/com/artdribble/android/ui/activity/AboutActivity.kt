package com.artdribble.android.ui.activity

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import com.artdribble.android.BuildConfig
import com.artdribble.android.R
import com.artdribble.android.utils.readTextFile
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Created by robcook on 6/18/17.
 */
class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initActionBar()
        initAbout()
        initVersion()
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        android.R.id.home -> consumeMenuItem { onBackPressed() }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initAbout() {
        val aboutHtml = readTextFile(resources.openRawResource(R.raw.about))
        val aboutSpanned: Spanned
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            aboutSpanned = Html.fromHtml(aboutHtml, Html.FROM_HTML_MODE_LEGACY)
        } else {
            aboutSpanned = Html.fromHtml(aboutHtml)
        }
        about_about.text = aboutSpanned
        about_about.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initVersion() {
        about_version.text = getString(R.string.about_version) + " ${BuildConfig.VERSION_NAME}"
    }
}