package data.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver

lateinit var appContext: Context

actual fun createDb(): MyDatabase {
    val driver = AndroidSqliteDriver(MyDatabase.Schema, appContext, "fortune.db")
    return MyDatabase(driver)
}


