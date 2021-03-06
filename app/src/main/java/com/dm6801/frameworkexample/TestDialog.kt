package com.dm6801.frameworkexample

import com.dm6801.framework.infrastructure.AbstractDialog
import com.dm6801.framework.utilities.Log

class TestDialog : AbstractDialog() {

    companion object : Comp<TestDialog>()

    override val layout = R.layout.dialog_test
    override val closeWithActivity = false

    override fun onSoftKeyboard(isVisible: Boolean, keyboardHeight: Int?) {
        super.onSoftKeyboard(isVisible, keyboardHeight)
        Log("keyboard visible: $isVisible - height: $keyboardHeight")
    }

}