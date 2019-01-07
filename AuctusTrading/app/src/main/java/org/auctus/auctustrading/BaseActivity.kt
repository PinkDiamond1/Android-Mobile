package org.auctus.auctustrading

import android.content.pm.ActivityInfo
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewStub
import android.widget.ProgressBar


abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getContentLayoutId(): Int

    abstract fun onCreateContent(content: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val vs = findViewById<ViewStub>(R.id.stub)

        vs.layoutResource = getContentLayoutId()
        val mainContent = vs.inflate()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        onCreateContent(mainContent)
    }

    protected fun showProgress() {
        findViewById<ProgressBar>(R.id.simpleProgressBar).visibility = View.VISIBLE
    }

    protected fun hideProgress() {
        findViewById<ProgressBar>(R.id.simpleProgressBar).visibility = View.INVISIBLE
    }
}
