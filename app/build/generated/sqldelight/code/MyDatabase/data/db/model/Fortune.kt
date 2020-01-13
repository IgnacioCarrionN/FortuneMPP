package data.db.model

import kotlin.Long
import kotlin.String

interface Fortune {
  val fortune_id: Long

  val fortune: String

  data class Impl(
    override val fortune_id: Long,
    override val fortune: String
  ) : Fortune {
    override fun toString(): String = """
    |Fortune.Impl [
    |  fortune_id: $fortune_id
    |  fortune: $fortune
    |]
    """.trimMargin()
  }
}
