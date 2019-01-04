package org.auctus.auctustrading

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    override fun getContentLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun onCreateContent(content: View) {
        navigateButton.setOnClickListener{
            test()
        }
    }

    private fun test(){
        val intent = Intent(this, WebViewActivity::class.java)
        startActivity(intent)
    }
}
