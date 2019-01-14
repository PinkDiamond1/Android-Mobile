package org.auctus.auctustrading

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject

class RegistrationActivity : BaseActivity() {

    override fun getContentLayoutId(): Int {
        return R.layout.activity_registration
    }

    override fun onCreateContent(content: View) {
        editDescription.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                description_counter.setText("${s.length} / 160")
            }
        })
    }

    fun onClickSubmit(v: View) {
        if (validateFields()) {
            inputLayoutName.isErrorEnabled = false
            inputLayoutEmail.isErrorEnabled = false
            inputLayoutPassword.isErrorEnabled = false

            showProgress()

            val params = JSONObject()
            params.put("name", editName.text)
            params.put("email", editEmail.text)
            params.put("password", editPassword.text)
            params.put("description", editDescription.text)
            params.put("referralCode", editInvitation.text)
            params.put("fromMobile", true)

            sendPostRequest("v1/accounts/register",
                    params,
                    Response.Listener {
                        hideProgress()
                        navigate(HomeActivity::class.java)
                    })
        }
    }

    private fun validateFields() : Boolean {
        var isValid = true

        if (TextUtils.isEmpty(editName.text)) {
            isValid = false
            inputLayoutName.isErrorEnabled = true
            inputLayoutName.error = getString(R.string.field_must_be_filled)
        }

        if (!ValidationUtil.isValidEmail(editEmail.text)) {
            isValid = false
            inputLayoutEmail.isErrorEnabled = true
            inputLayoutEmail.error = getString(R.string.field_must_be_filled)
        }

        if (TextUtils.isEmpty(editPassword.text)) {
            isValid = false
            inputLayoutPassword.isErrorEnabled = true
            inputLayoutPassword.error = getString(R.string.field_must_be_filled)
        }

        return isValid
    }
}
