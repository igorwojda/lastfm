package com.igorwojda.showcase.app.presentation

import android.os.Bundle
import com.igorwojda.showcase.R
import com.igorwojda.showcase.base.presentation.activity.BaseContainerActivity

class MainActivity : BaseContainerActivity() {

    override val layoutResourceId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AlbumSearchActivity.start(this)
    }
}
