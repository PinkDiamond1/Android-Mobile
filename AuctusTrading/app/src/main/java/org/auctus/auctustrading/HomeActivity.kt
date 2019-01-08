package org.auctus.auctustrading

import android.view.View

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
