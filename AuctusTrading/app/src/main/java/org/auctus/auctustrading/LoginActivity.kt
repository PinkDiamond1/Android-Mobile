package org.auctus.auctustrading

import android.view.View
import com.android.volley.Response
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreateContent(content: View) {

    }

    fun navigateToForgotPassword(v: View){
        navigate(ForgotPasswordActivity::class.java)
    }

    fun onClickEnter(v: View) {
        if (validate()) {
            inputLayoutEmail.isErrorEnabled = false
            showProgress()
            val params = JSONObject()
            params.put("email", emailInput.text)
            params.put("password", passwordInput.text)
            params.put("fromMobile", true)
            sendPostRequest("v1/accounts/login",
                    params,
                    Response.Listener<JSONObject> { response ->
                        hideProgress()
                        navigate(HomeActivity::class.java)
                    })
        }
        else {
            inputLayoutEmail.isErrorEnabled = true
            inputLayoutEmail.error = "Please inform a valid email address."
        }
    }

    private fun validate() : Boolean {
        return ValidationUtil.isValidEmail(emailInput.text)
    }
}
