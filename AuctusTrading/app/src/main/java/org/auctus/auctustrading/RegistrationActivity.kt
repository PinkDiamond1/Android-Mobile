package org.auctus.auctustrading

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_registration.*
import android.Manifest.permission
import android.content.pm.PackageManager
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


private val MY_PERMISSIONS_REQUEST_USE_CAMERA = 0x00AF
class RegistrationActivity : BaseActivity() {
    val PICK_IMAGE = 0
    val TAKE_PICTURE = 1

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
    fun onGalleryClicked(view: View) {
        val intent = Intent();
        intent.type = "image/*";
        intent.action = Intent.ACTION_GET_CONTENT;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    fun onPictureClicked(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_USE_CAMERA);
        } else {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, TAKE_PICTURE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_USE_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, TAKE_PICTURE)
                } else {
                }
                return
            }
        }
    }

    fun onAddPictureClick(view: View){
        val fm = supportFragmentManager
        val cameraGalleryPicker = CameraGaleryPicker.newInstance()
        cameraGalleryPicker.show(fm, "fragment_camera_gallery")
    }

     fun onActivityResult(requestCode: Int) {
        if (requestCode == PICK_IMAGE) {

        }
    }
}
