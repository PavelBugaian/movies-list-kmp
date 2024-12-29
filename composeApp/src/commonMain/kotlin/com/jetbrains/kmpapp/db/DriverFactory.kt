package com.jetbrains.kmpapp.db

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.kmpapp.db.AppDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()
    val database = AppDatabase(driver)
    return database
}
