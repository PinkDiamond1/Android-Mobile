package org.auctus.auctustrading

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebSettings
import android.content.pm.ApplicationInfo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        setContentView(R.layout.activity_main)
        mainView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        mainView.settings.javaScriptEnabled = true
        mainView.settings.domStorageEnabled = true
        mainView.loadUrl("https://auctusplatformwebdev.azurewebsites.net/")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getAction() === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (mainView.canGoBack()) {
                        mainView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }

        }
        return super.onKeyDown(keyCode, event)
    }
}
