package org.auctus.auctustrading

import android.text.TextUtils



class ValidationUtil {
    companion object {
        fun isValidEmail(target: CharSequence): Boolean {
            return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}