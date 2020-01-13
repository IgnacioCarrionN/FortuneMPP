package data.db

import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver

actual fun createDb(): MyDatabase {
    return MyDatabase(NativeSqliteDriver(MyDatabase.Schema, "fortune.db"))
}