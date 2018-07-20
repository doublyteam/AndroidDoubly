package com.doublyapp.doubly

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_authenticate.*
import java.io.Serializable

/**
 * Created by edwardpoon on 7/17/18.
 */
class AuthenticateFragment : Fragment() {
    private val EXTRA_LISTENER = "EXTRA_LISTENER"
    val LOG_TAG: String = "AuthenticateFragment"
    var  mOnLoggedInCallback: OnLoggedInCallback? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mOnLoggedInCallback = arguments.get(EXTRA_LISTENER) as OnLoggedInCallback?
        return inflater!!.inflate(R.layout.fragment_authenticate, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resumeViews();
    }

    private fun resumeViews() {
        if(!EmailValidator.isValidEmail(edittext_email.text.toString())){
            button_login.isEnabled = false
            button_signup.isEnabled = false
        }
        edittext_email.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!EmailValidator.isValidEmail(p0.toString())){
                    button_login.isEnabled = false
                    button_signup.isEnabled = false
                }
                else {
                    button_login.isEnabled = true
                    button_signup.isEnabled = true
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!EmailValidator.isValidEmail(p0.toString())){
                    button_login.isEnabled = false
                    button_signup.isEnabled = false
                }
                else {
                    button_login.isEnabled = true
                    button_signup.isEnabled = true
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
        button_login.setOnClickListener {
            val email = edittext_email.text.toString()
            val password = edittext_password.text.toString()
            Auth.login(email, password) { isSuccess: Boolean, exception: Exception? ->
                if (isSuccess) {
                    // Successful login Taket the user to the courses page
                    mOnLoggedInCallback?.onLoggedIn()
                } else {
                    // Tell the user to try again
                    MessageAndOkButtonDialogFragment.newInstance(getString(R.string.authenticate_invalid_credentials)).show(fragmentManager, "Invalid Credentials")
                }
            }
        }

        button_signup.setOnClickListener{
            val email = edittext_email.text.toString()
            val password = edittext_password.text.toString()
            Log.d(LOG_TAG, "Sign up initiated")
            Log.d(LOG_TAG, "Email: ${email}")
            Auth.signUp(email, password) { isSuccess: Boolean, exception: Exception? ->
                if (isSuccess) {
                    Log.d(LOG_TAG, "Sign up successful")
                    MessageAndOkButtonDialogFragment.newInstance(getString(R.string.authenticate_email_verification_text)).show(fragmentManager, "Successful Signup")
                } else {
                    Log.d(LOG_TAG, "Sign up fail")
                    MessageAndOkButtonDialogFragment.newInstance(exception?.localizedMessage ?: "Failed to sign up. Please try again.").show(fragmentManager, "Unsuccessful Signup")
                }
            }
        }
    }

    companion object {
        private val EXTRA_LISTENER = "EXTRA_LISTENER"
        fun newInstance(onLoggedInCallback: OnLoggedInCallback): AuthenticateFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_LISTENER, onLoggedInCallback)
            val fragment = AuthenticateFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnLoggedInCallback: Serializable {
        fun onLoggedIn()
    }
}