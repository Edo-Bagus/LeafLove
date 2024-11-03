package com.example.leaflove

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.leaflove.data.database.AppDatabase

class MyApp : Application() {
    companion object {
        private lateinit var instance: MyApp
        fun getDatabase(): AppDatabase {
            return instance.database
        }
    }

    private lateinit var database: AppDatabase
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // SQL statements to update the schema, e.g., add a new column
            database.execSQL("ALTER TABLE plant_detail ADD COLUMN family TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN origin TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN type TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN dimension TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN dimensions TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN attracts TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN propagation TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN hardiness TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN hardiness_location TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN depth_water_requirement TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN volume_water_requirement TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN watering_period TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN watering_general_benchmark TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN plant_anatomy TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN pruning_month TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN pruning_count TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN seeds INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN maintenance TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN care_guides TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN soil TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN growth_rate TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN drought_tolerant INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN salt_tolerant INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN thorny INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN invasive INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN tropical INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN indoor INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN care_level TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN pest_susceptibility TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN pest_susceptibility_api TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN flowers INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN flowering_season TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN flower_color TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN cones INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN fruits INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN edible_fruit INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN edible_fruit_taste_profile TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN fruit_nutritional_value TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN fruit_color TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN harvest_season TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN leaf INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN leaf_color TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN edible_leaf INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN cuisine INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN medicinal INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN poisonous_to_humans INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN poisonous_to_pets INTEGER;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN description TEXT;\n" +
                    "ALTER TABLE plant_detail ADD COLUMN other_images TEXT;")
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // Initialize the singleton instance
        // Initialize the Room database here
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "plant-database"
        ).addMigrations(MIGRATION_1_2).build()
    }

    // Provide a method to access the DAO
    fun getPlantSpeciesDao() = database.plantSpeciesDao()
}