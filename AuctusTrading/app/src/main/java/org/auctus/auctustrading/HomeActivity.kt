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

    }

    fun navigateToLogin(v: View){
        navigate(LoginActivity::class.java)
    }

    fun navigateToRegistration(v: View){
        navigate(RegistrationActivity::class.java)
    }
}
