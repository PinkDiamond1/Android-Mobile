package org.auctus.auctustrading

import android.view.View

class LoginActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreateContent(content: View) {

    }

    fun navigateToForgotPassword(v: View){
        navigate(ForgotPasswordActivity::class.java)
    }
}
