package com.bignerdranch.android.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.criminalintent.Crime

@Database(entities = [Crime::class], version = 4, exportSchema = false)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}

val MIGRATION_3_2 = object : Migration(3, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime RENAME TO oldCrime"
        )
        database.execSQL(
            "CREATE TABLE Crime (id BLOB NOT NULL, title TEXT NOT NULL, date INTEGER NOT NULL, isSolved INTEGER NOT NULL, suspect TEXT NOT NULL, PRIMARY KEY(id)) "
        )
        database.execSQL(
            "INSERT INTO Crime (id, title, date, isSolved, suspect) SELECT id, title, date, isSolved, suspect FROM oldCrime"
        )
        database.execSQL(
            "DROP TABLE oldCrime"
        )
    }
}

val MIGRATION_2_4 = object : Migration(2, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime ADD COLUMN photoFileName Text"
        )
    }
}