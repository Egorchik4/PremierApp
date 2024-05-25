package com.example.premierapp.app

import android.app.Application
import com.example.premierapp.di.AppComponent
import com.example.premierapp.di.DaggerAppComponent

class App : Application() {

	lateinit var appComponent: AppComponent

	override fun onCreate() {
		super.onCreate()

		appComponent = DaggerAppComponent
			.builder()
			.context(context = this)
			.build()
	}
}