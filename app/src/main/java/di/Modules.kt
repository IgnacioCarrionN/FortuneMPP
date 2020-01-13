package di

import androidx.lifecycle.ViewModelProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import sample.FortuneViewModel
import sample.FortuneViewModelFactory

actual val client: HttpClient = HttpClient(OkHttp) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json.nonstrict)
    }
}

val fortuneViewmodelModule = Kodein.Module(name = "FortuneViewModel") {
    bind<FortuneViewModel>(tag = FortuneViewModel::class.java.simpleName) with provider {
        FortuneViewModel(instance())
    }

    bind<ViewModelProvider.Factory>() with singleton { FortuneViewModelFactory(instance()) }
}