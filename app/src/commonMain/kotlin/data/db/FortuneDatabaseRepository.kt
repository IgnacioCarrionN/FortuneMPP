package data.db

import api.Fortune
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// TODO END DATABASE WITH FLOW INTEGRATION
class FortuneDatabaseRepository {

    private val fortuneDB = createDb()
    private val fortuneQueries = fortuneDB.fortuneModelQueries

    fun saveFortune(fortune: Fortune) =
        GlobalScope.launch {
            fortuneQueries.insertItem(fortune.fortune)
        }


    suspend fun getAllFortunes(): Flow<List<Fortune>> =
        fortuneQueries.selectAll(mapper = { _, fortune ->
            Fortune(fortune)
        }).asFlow().mapToList()


}