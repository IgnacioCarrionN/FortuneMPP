package sample

import android.app.Application
import data.db.appContext
import di.commonModule
import di.fortuneViewmodelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class FortuneApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@FortuneApplication))
        import(commonModule)
        import(fortuneViewmodelModule)
    }
}