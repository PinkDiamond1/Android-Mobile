package org.auctus.auctustrading

import android.view.View
import com.android.volley.Response
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task




private const val GOOGLE_ACCOUNT_LOGIN = 0x6006
private const val FACEBOOK_LOGIN = 0x00FB

class LoginActivity : BaseActivity(){

    private var mGoogleSignInClient : GoogleSignInClient? = null

    override fun getContentLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreateContent(content: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //TODO: login automatically by getting last logged user
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //Note: If you need to detect changes to a user's auth state that happen outside your app, such as access token or ID token revocation, or to perform cross-device sign-in, you might also call GoogleSignInClient.silentSignIn when your app starts.

    }

    fun onClickGoogleLogin(v:View){
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_ACCOUNT_LOGIN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_ACCOUNT_LOGIN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            //
            val test = task.result
        }
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
