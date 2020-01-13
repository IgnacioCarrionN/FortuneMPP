package di

import api.FortuneApi
import io.ktor.client.HttpClient
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import repository.FortuneRepository

val commonModule = Kodein.Module(name="commonModule") {
    bind<FortuneApi>() with provider { FortuneApi(client) }
    bind<FortuneRepository>() with singleton { FortuneRepository(instance()) }
}

expect val client: HttpClient