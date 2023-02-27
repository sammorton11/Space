package com.example.space

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

object TestContext {
    var appContext: Context = InstrumentationRegistry.getInstrumentation().context
}