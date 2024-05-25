package com.example.premierapp.di

import android.content.Context
import com.example.premierapp.presentation.authorization.AuthorizationFragment
import com.example.premierapp.presentation.list.ListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SingletonModule::class, ViewModelModule::class])
interface AppComponent {

	@Component.Builder
	interface Builder {

		@BindsInstance
		fun context(context: Context): Builder
		fun build(): AppComponent
	}

	val factory: MultiViewModelFactory

	fun inject(authorizationFragment: AuthorizationFragment)
	fun inject(authorizationFragment: ListFragment)
}