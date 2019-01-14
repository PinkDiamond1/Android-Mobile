package org.auctus.auctustrading

import android.view.View
import android.widget.Toast
import com.android.volley.Response
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject


class ForgotPasswordActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {
        return R.layout.activity_forgot_password
    }

    override fun onCreateContent(content: View) {

    }

    fun onClickSend(v: View) {
        if (validate()) {
            input_layout_email.isErrorEnabled = false
            showProgress()
            val params = JSONObject()
            params.put("email", editEmail.text)
            params.put("fromMobile", true)

            sendPostRequest("v1/accounts/passwords/recover",
                    params,
                    Response.Listener<JSONObject> { response ->
                        hideProgress()
                        navigate(HomeActivity::class.java)
                    })
        }
        else {
            input_layout_email.isErrorEnabled = true
            input_layout_email.error = "Please inform a valid email address."
        }
    }

    private fun validate() : Boolean {
        return ValidationUtil.isValidEmail(editEmail.text)
    }
}
