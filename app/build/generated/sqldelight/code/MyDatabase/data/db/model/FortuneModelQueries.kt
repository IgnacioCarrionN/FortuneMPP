package data.db.model

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Long
import kotlin.String

interface FortuneModelQueries : Transacter {
  fun <T : Any> selectAll(mapper: (fortune_id: Long, fortune: String) -> T): Query<T>

  fun selectAll(): Query<Fortune>

  fun insertItem(fortune: String)
}
