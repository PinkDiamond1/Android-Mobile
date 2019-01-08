package org.auctus.auctustrading

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
import com.android.volley.toolbox.StringRequest


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

    protected fun sendPostRequest(url: String, successListener: Response.Listener<String>) {
        sendRequest(Request.Method.POST, url, successListener)
    }

    protected fun sendGetRequest(url: String, successListener: Response.Listener<String>) {
        sendRequest(Request.Method.GET, url, successListener)
    }

    protected fun sendRequest(requestMethod: Int, url: String, successListener: Response.Listener<String>) {
        val stringRequest = StringRequest(requestMethod, "${BuildConfig.SERVER_URL}${url}",
                successListener, getErrorDialogListener())

        RequestQueueSingleton.getInstance(this.applicationContext).addToRequestQueue(stringRequest)
    }

    private fun getErrorDialogListener() : Response.ErrorListener {
        return Response.ErrorListener {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Alert")
            alertDialog.setMessage("The request could not be processed")
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            alertDialog.show()
            hideProgress()
        }
    }
}
