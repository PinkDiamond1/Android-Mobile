package org.auctus.auctustrading

import android.view.View
import android.widget.Toast
import com.android.volley.Response
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.json.JSONObject


class ForgotPasswordActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {
        return R.layout.activity_forgot_password
    }

    override fun onCreateContent(content: View) {

    }

    fun onClickSend(v: View) {
        showProgress()
        val params = JSONObject()
        params.put("email", editEmail.text)
        sendPostRequest("v1/accounts/passwords/recover",
                params,
                Response.Listener<JSONObject> { response ->
                    hideProgress()
                    navigate(HomeActivity::class.java)
                })
    }
}
