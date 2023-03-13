package com.samm.space.util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

object TestContext {
    var appContext: Context = InstrumentationRegistry.getInstrumentation().context
}