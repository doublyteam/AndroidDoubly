package com.doublyapp.doubly

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

class MainActivity : SingleFragmentActivity(), AuthenticateFragment.OnLoggedInCallback {
    private val LOG_TAG: String? = "MainActivity"

    override fun createFragment(): Fragment {
        if (!Auth.isUserLoggedIn()) {
            // No current user so we should present the signup/login page
            Log.d(LOG_TAG, "User is NOT logged in")
            return AuthenticateFragment.newInstance(this)
        } else {
            // There is a current user so we should display the Courses Fragment
            Log.d(LOG_TAG, "User is logged in")
            return CoursesFragment.newInstance()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    override fun onLoggedIn() {
        addFragment(CoursesFragment.newInstance())
    }

}
