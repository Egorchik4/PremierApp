package com.example.premierapp

import android.app.Application
import com.example.premierapp.di.AppComponent
import com.example.premierapp.di.DaggerAppComponent

class App : Application() {

	private lateinit var appComponent: AppComponent

	override fun onCreate() {
		super.onCreate()

		appComponent = DaggerAppComponent.create()
	}
}