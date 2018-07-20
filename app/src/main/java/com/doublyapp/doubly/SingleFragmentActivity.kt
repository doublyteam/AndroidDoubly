package com.doublyapp.doubly

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by edwardpoon on 7/17/18.
 */
abstract class SingleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(fragment == null) {
            fragment = createFragment();
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    abstract fun createFragment(): Fragment

    fun addFragment(fragmentToBeAdded: Fragment) {
        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragmentToBeAdded).commit();
    }
}