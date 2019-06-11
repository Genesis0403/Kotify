package com.epam.kotify.utils

import android.content.Context
import java.lang.ref.WeakReference

/**
 * Wrapper class which contains weak reference to Application Context.
 *
 * @author Vlad Korotkevich
 */

data class AppContextProvider(val context: WeakReference<Context>) {

    fun getString(id: Int) = context.get()?.getString(id)
}