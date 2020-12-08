package com.dm6801.framework.ui

import android.view.View
import android.widget.Checkable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun <T : View> T.onClick(disableFor: Long? = 1000, action: ((T) -> Unit)?) {
    if (action == null) setOnClickListener(null)
    else setOnClickListener(ViewClickListener(disableFor, action))
}

class ViewClickListener<T : View?, R>(
    disableFor: Long? = NO_TIMEOUT,
    private val block: (T) -> R
) : View.OnClickListener {

    companion object {
        const val DEFAULT_TIMEOUT = 500L
        const val NO_TIMEOUT = -1L
    }

    private var lastClickTime = 0L
    private val disableFor: Long = disableFor ?: DEFAULT_TIMEOUT

    @Suppress("UNCHECKED_CAST")
    override fun onClick(v: View?) {
        when (v) {
            is Checkable -> {
                val now = System.currentTimeMillis()
                if (now - lastClickTime < disableFor) {
                    v.isChecked = !v.isChecked
                    return
                }
                lastClickTime = now
                (v as? T)?.let { view -> block(view) }
            }
            else ->{
                v?.isClickable = false
                CoroutineScope(Dispatchers.Main).launch {
                    kotlinx.coroutines.delay(disableFor)
                    v?.isClickable = true
                }
                (v as? T)?.let{ block(it) }
            }

        }
    }

}
