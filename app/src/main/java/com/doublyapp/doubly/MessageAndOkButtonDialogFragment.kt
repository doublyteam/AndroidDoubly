package com.doublyapp.doubly

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

/**
 * Created by edwardpoon on 7/19/18.
 */
class MessageAndOkButtonDialogFragment: DialogFragment() {
    companion object {

        fun newInstance(message: String): MessageAndOkButtonDialogFragment {
            val fragment = MessageAndOkButtonDialogFragment()
            val args = Bundle()
            args.putString("EXTRA_MESSAGE", message)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val message = arguments.getString("EXTRA_MESSAGE")
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
                .setPositiveButton(R.string.dialog_ok_button, null)
        return builder.create()

    }
}