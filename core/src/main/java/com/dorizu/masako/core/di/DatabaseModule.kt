package com.dorizu.masako.core.di

import android.content.Context
import androidx.room.Room
import com.dorizu.masako.core.data.source.local.room.MasakoDatabase
import com.dorizu.masako.core.data.source.local.room.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MasakoDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("masako".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            MasakoDatabase::class.java, "Masako.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideRecipesDao(database: MasakoDatabase): RecipesDao = database.recipesDao()
}