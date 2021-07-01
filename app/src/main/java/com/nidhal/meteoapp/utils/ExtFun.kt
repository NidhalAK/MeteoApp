package com.nidhal.meteoapp.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentTransaction


/**
 * Load a fragment transaction to the activity state.
 *
 * @param isAddToBackStack Optional if you will replace a fragment without adding it to back Stack,
 * default value is False
 * @param transitionPairs Map of name [String] and View
 */
inline fun AppCompatActivity.loadFragment(
    isAddToBackStack: Boolean = false,
    transitionPairs: Map<String, View> = mapOf(),
    transaction: FragmentTransaction.() -> Unit
) {
    val beginTransaction = supportFragmentManager.beginTransaction()
    beginTransaction.transaction()
    for ((name, view) in transitionPairs) {
        ViewCompat.setTransitionName(view, name)
        beginTransaction.addSharedElement(view, name)
    }

    if (isAddToBackStack) beginTransaction.addToBackStack(null)
    beginTransaction.commit()
}