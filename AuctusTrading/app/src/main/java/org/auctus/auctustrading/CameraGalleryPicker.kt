package org.auctus.auctustrading

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
 const val GALLERY = 0
 const val PICTURE = 1

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CameraGaleryPicker.onCameraGalleryPickerInteraction] interface
 * to handle interaction events.
 * Use the [CameraGaleryPicker.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CameraGaleryPicker : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_gallery_picker, container, false)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */

    companion object {
        @JvmStatic
        fun newInstance() = CameraGaleryPicker()
    }
}
