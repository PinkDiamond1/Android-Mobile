package org.auctus.auctustrading

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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
        showProgress()
        val params = JSONObject()
        params.put("name", editName.text)
        params.put("email", editEmail.text)
        params.put("password", editPassword.text)
        params.put("description", editDescription.text)
        params.put("referralCode", editInvitation.text)
        params.put("fromMobile", true)

        sendPostRequest("v1/advisors",
                params,
                Response.Listener<JSONObject> { response ->
                    hideProgress()
                    navigate(HomeActivity::class.java)
                })
    }

    fun submit(v: View) {
        val url = "${BuildConfig.SERVER_URL}v1/advisors"
        val jsonObjRequest = object : StringRequest(Request.Method.POST,
                url,
                Response.Listener<String> { response ->
                    hideProgress()
                    navigate(HomeActivity::class.java)
                },
                getErrorDialogListener()) {

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded; charset=UTF-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return "name=aa&email=aa@aaa.com&password=bb&description=xulambis&fromMobile=true".toByteArray()
            }

        }

        RequestQueueSingleton.getInstance(this.applicationContext).addToRequestQueue(jsonObjRequest)
    }
}
