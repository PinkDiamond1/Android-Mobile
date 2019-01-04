package org.auctus.auctustrading

import android.view.View
import android.widget.Toast



class ForgotPasswordActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {
        return R.layout.activity_forgot_password
    }

    override fun onCreateContent(content: View) {

    }

    fun onClickShow(v: View) {
        showProgress()
    }

    fun onClickHide(v: View) {
        hideProgress()
    }
}
