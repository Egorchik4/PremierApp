package com.example.premierapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.premierapp.data.datasource.MealDataSource
import com.example.premierapp.data.datasource.MealDataSourceImpl
import com.example.premierapp.data.datasource.api.MealApi
import com.example.premierapp.data.repository.MealRepositoryImpl
import com.example.premierapp.domain.repository.MealRepository
import com.example.premierapp.domain.usecase.GetMealsUseCase
import com.example.premierapp.domain.usecase.GetOfInSharedPreferencesUseCase
import com.example.premierapp.domain.usecase.PutInSharedPreferencesUseCase
import com.example.premierapp.domain.usecase.ValidationUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val APP_PREFERENCES = "APP_PREFERENCES"

@Module
class SingletonModule {

	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit {
		return Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://themealdb.com/api/json/v1/1/")
			.build()
	}

	@Provides
	@Singleton
	fun provideMealApi(retrofit: Retrofit): MealApi {
		return retrofit.create(MealApi::class.java)
	}

	@Provides
	@Singleton
	fun provideMealDataSource(api: MealApi): MealDataSource {
		return MealDataSourceImpl(api)
	}

	@Provides
	@Singleton
	fun provideMealRepository(mealDataSource: MealDataSource): MealRepository {
		return MealRepositoryImpl(mealDataSource)
	}

	@Provides
	@Singleton
	fun provideGetMealsUseCase(mealRepository: MealRepository): GetMealsUseCase {
		return GetMealsUseCase(mealRepository)
	}

	@Provides
	@Singleton
	fun provideValidationUseCase(): ValidationUseCase {
		return ValidationUseCase()
	}

	@Provides
	@Singleton
	fun provideSharedPrefStorage(context: Context): SharedPreferences {
		return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
	}

	@Provides
	@Singleton
	fun providePutInSharedPreferences(sharedPreferences: SharedPreferences): PutInSharedPreferencesUseCase {
		return PutInSharedPreferencesUseCase(sharedPreferences)
	}

	@Provides
	@Singleton
	fun provideGetOfInSharedPreferences(sharedPreferences: SharedPreferences): GetOfInSharedPreferencesUseCase {
		return GetOfInSharedPreferencesUseCase(sharedPreferences)
	}
}