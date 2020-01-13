package sample

import android.os.Bundle
import android.widget.LinearLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import api.FortuneResponse
import di.fortuneViewmodelModule
import io.ktor.util.InternalAPI
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.erased.instance
import sample.recycler.FortuneAdapter
import kotlin.coroutines.CoroutineContext

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android"
}


@InternalAPI
class MainActivity : AppCompatActivity(), CoroutineScope, KodeinAware {

    override val kodein: Kodein by lazy{
        (applicationContext as FortuneApplication).kodein
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    private val fabUpdate by lazy { findViewById<FloatingActionButton>(R.id.fab_update) }
    private val mainLabel by lazy { findViewById<TextView>(R.id.main_text) }
    private val fortuneHistory by lazy { findViewById<RecyclerView>(R.id.rv_fortune_history) }


    private val viewModelFactory: ViewModelProvider.Factory by instance()

    private val viewModel: FortuneViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(FortuneViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sample().checkMe()
        setContentView(R.layout.activity_main)

        fabUpdate.setOnClickListener {
            updateFortune()
        }

        viewModel.fortuneLiveData.observe(this@MainActivity, Observer {  value ->

            when(value) {
                is FortuneResponse.Error -> mainLabel.text = value.error
                is FortuneResponse.Success -> mainLabel.text = value.response.fortune
                is FortuneResponse.Loading -> mainLabel.text = "LOADING"
            }

        })


        viewModel.fortuneListLiveData.observe(this@MainActivity, Observer { list ->
            if(list.isNotEmpty()) {
                (fortuneHistory.adapter as? FortuneAdapter)?.updateData(list)
            }
        })

        fortuneHistory.apply {
            adapter = FortuneAdapter()
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            layoutManager = linearLayoutManager
        }

        updateFortune()
    }

    private fun updateFortune() {
        viewModel.getFortune()
    }



}