package com.example.leaflove.di

import androidx.room.Room
import com.example.leaflove.data.dao.PlantDetailDao
import com.example.leaflove.data.dao.PlantDetailDao_Impl
import com.example.leaflove.data.dao.PlantSpeciesDao
import com.example.leaflove.data.dao.PlantSpeciesDao_Impl
import com.example.leaflove.data.database.AppDatabase
import com.example.leaflove.data.repositories.PlantRepository
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.example.leaflove.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


var appModule = module {

    single{
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "plant-database"
        ).build()

    }

    single<PlantSpeciesDao>{
        PlantSpeciesDao_Impl(get<AppDatabase>());
    }

    single<PlantDetailDao>{
        PlantDetailDao_Impl(get<AppDatabase>())
    }

    single{
        PlantRepository(get(), get());
    }

    // Provide the ViewModels, injecting the repository
    viewModel { AuthViewModel() } // If AuthViewModel needs dependencies, pass them here
    viewModel { PlantViewModel(get(), get()) } // Injecting PlantRepository into PlantViewModel
    viewModel { LocationViewModel() }
    viewModel { WeatherViewModel() }
}