package org.auctus.auctustrading

import android.Manifest
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_registration.*
import android.content.pm.PackageManager
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import com.google.android.gms.auth.api.signin.GoogleSignInOptions




private const val PERMISSIONS_REQUEST_USE_CAMERA = 0x00AF
private const val PERMISSIONS_REQUEST_READ_STORAGE = 0X00B0

class RegistrationActivity : BaseActivity() {
    val PICK_IMAGE = 0
    val TAKE_PICTURE = 1

    var cameraGalleryPicker: DialogFragment? = null
    var encodedSelectedImage  = ""


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
    fun onAddPictureClick(view: View){
        val fm = supportFragmentManager
        cameraGalleryPicker = CameraGaleryPicker.newInstance()
        cameraGalleryPicker?.show(fm, "fragment_camera_gallery")
    }

    fun onGalleryClicked(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_READ_STORAGE);
        } else {
            launchGalleryIntent()
        }
    }

    private fun launchGalleryIntent(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    fun onTakePictureClicked(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_USE_CAMERA);
        } else {
            launchCameraIntent()
        }
    }

    private fun launchCameraIntent(){
        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                .also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        startActivityForResult(takePictureIntent, TAKE_PICTURE)
                    }
                }
    }

    fun onRemovePictureClicked(view: View) {
        registration_avatar.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.avatar ))
        encodedSelectedImage = ""
        cameraGalleryPicker?.dismissAllowingStateLoss()
    }

   override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_USE_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCameraIntent()
                }
                return
            }
            PERMISSIONS_REQUEST_READ_STORAGE ->{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchGalleryIntent()
                }
                return
            }
        }
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
             val imageBitmap = data?.extras?.get("data") as Bitmap
             registration_avatar.setImageBitmap(imageBitmap)
             encodedSelectedImage = encodeImage(imageBitmap)
             cameraGalleryPicker?.dismissAllowingStateLoss()
         }

         else if ( requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
             val imageUri = data?.data
             val imageStream = contentResolver.openInputStream(imageUri)
             val imageBitmap = BitmapFactory.decodeStream(imageStream)
             registration_avatar.setImageBitmap(imageBitmap)
             encodedSelectedImage = encodeImage(imageBitmap)
             cameraGalleryPicker?.dismissAllowingStateLoss()
         }
     }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bArray = baos.toByteArray()

        return android.util.Base64.encodeToString(bArray, android.util.Base64.DEFAULT)
    }

    fun onClickSubmit(v: View) {
        if (validateFields()) {
            clearError()

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
        clearError()
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

    private fun clearError() {
        inputLayoutName.isErrorEnabled = false
        inputLayoutEmail.isErrorEnabled = false
        inputLayoutPassword.isErrorEnabled = false
    }
}
