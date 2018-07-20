package com.doublyapp.doubly

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by edwardpoon on 7/18/18.
 * This class is a wrapper around the Firebase Authentication Framework. This is just in case we
 * decide to switch our authentication provider.
 */
class Auth {
    companion object {
        private val LOG_TAG: String = "Auth"
        fun isUserLoggedIn(): Boolean {
            Log.d(LOG_TAG, "Current user is:  ${FirebaseAuth.getInstance().currentUser?.email}")
            Log.d(LOG_TAG, "Is Email verified? ${FirebaseAuth.getInstance().currentUser?.isEmailVerified
                    ?: false}")
            val result: Boolean = FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.isEmailVerified ?: false
            Log.d(LOG_TAG, "Is user logged in? ${result}")
            return result
        }

        fun currentUser(): FirebaseUser {
            return FirebaseAuth.getInstance().currentUser!!
        }

        fun login(email: String, password: String, callback: (isSuccess: Boolean, exception: Exception?) -> Unit) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                callback(it.isSuccessful, it.exception)
            }
        }

        fun signUp(email: String, password: String, callback: (isSuccess: Boolean, exception: Exception?) -> Unit) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    sendSignUpVerificationEmail(callback)
                } else {
                    callback(it.isSuccessful, it.exception)
                }
            }
        }

        private fun sendSignUpVerificationEmail(callback: (isSuccess: Boolean, exception: Exception?) -> Unit) {
            FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener {
                callback(it.isSuccessful, it.exception);
            }
        }
    }
}