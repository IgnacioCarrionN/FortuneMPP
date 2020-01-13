package data.db

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import data.db.app.newInstance
import data.db.app.schema
import data.db.model.FortuneModelQueries

interface MyDatabase : Transacter {
  val fortuneModelQueries: FortuneModelQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = MyDatabase::class.schema

    operator fun invoke(driver: SqlDriver): MyDatabase = MyDatabase::class.newInstance(driver)}
}
