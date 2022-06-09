package kz.abudinislam.retrofitjas

import android.app.Application
import android.widget.Button
import kz.abudinislam.retrofitjas.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

}