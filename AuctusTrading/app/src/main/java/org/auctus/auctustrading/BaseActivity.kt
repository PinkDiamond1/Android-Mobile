package org.auctus.auctustrading

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewStub
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.EditText
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.android.volley.VolleyError
import android.R.attr.data




abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getContentLayoutId(): Int

    abstract fun onCreateContent(content: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val vs = findViewById<ViewStub>(R.id.stub)

        vs.layoutResource = getContentLayoutId()
        val mainContent = vs.inflate()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        onCreateContent(mainContent)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    protected fun showProgress() {
        findViewById<ProgressBar>(R.id.simpleProgressBar).visibility = View.VISIBLE
    }

    protected fun hideProgress() {
        findViewById<ProgressBar>(R.id.simpleProgressBar).visibility = View.INVISIBLE
    }

    protected fun navigate(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    protected fun sendPostRequest(url: String, requestObject: JSONObject,
                                  successListener: Response.Listener<JSONObject>) {
        sendRequest(Request.Method.POST, url, requestObject, successListener)
    }

    protected fun sendGetRequest(url: String, requestObject: JSONObject,
                                 successListener: Response.Listener<JSONObject>) {
        sendRequest(Request.Method.GET, url, requestObject, successListener)
    }

    protected fun sendRequest(requestMethod: Int, url: String, requestObject: JSONObject,
                              successListener: Response.Listener<JSONObject>) {
        val jsonRequest = object : JsonObjectRequest(requestMethod, "${BuildConfig.SERVER_URL}${url}",
                requestObject, successListener, getErrorDialogListener()) {
            override fun parseNetworkError(volleyError: VolleyError?): VolleyError {
                if (volleyError?.networkResponse != null && volleyError.networkResponse.data != null) {
                    return VolleyError(String(volleyError.networkResponse.data))
                }
                return super.parseNetworkError(volleyError)
            }
        }

        RequestQueueSingleton.getInstance(this.applicationContext).addToRequestQueue(jsonRequest)
    }

    protected fun getErrorDialogListener() : Response.ErrorListener {
        return Response.ErrorListener {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Alert")
            alertDialog.setMessage(it.message)
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            alertDialog.show()
            hideProgress()
        }
    }
}
