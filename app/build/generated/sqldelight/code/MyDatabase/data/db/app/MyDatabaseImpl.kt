package data.db.app

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import data.db.MyDatabase
import data.db.model.Fortune
import data.db.model.FortuneModelQueries
import kotlin.Any
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.reflect.KClass

internal val KClass<MyDatabase>.schema: SqlDriver.Schema
  get() = MyDatabaseImpl.Schema

internal fun KClass<MyDatabase>.newInstance(driver: SqlDriver): MyDatabase = MyDatabaseImpl(driver)

private class MyDatabaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), MyDatabase {
  override val fortuneModelQueries: FortuneModelQueriesImpl = FortuneModelQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE Fortune(
          |fortune_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
          |fortune TEXT NOT NULL
          |)
          """.trimMargin(), 0)
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ) {
    }
  }
}

private class FortuneModelQueriesImpl(
  private val database: MyDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), FortuneModelQueries {
  internal val selectAll: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> selectAll(mapper: (fortune_id: Long, fortune: String) -> T): Query<T> =
      Query(267048914, selectAll, driver, "FortuneModel.sq", "selectAll", "SELECT * FROM Fortune") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!
    )
  }

  override fun selectAll(): Query<Fortune> = selectAll(Fortune::Impl)

  override fun insertItem(fortune: String) {
    driver.execute(-1984017857, """INSERT OR REPLACE INTO Fortune(fortune) VALUES (?1)""", 1) {
      bindString(1, fortune)
    }
    notifyQueries(-1984017857, {database.fortuneModelQueries.selectAll})
  }
}
