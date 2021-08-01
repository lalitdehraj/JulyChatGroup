package com.example.july.di

import android.content.Context
import androidx.room.Room
import com.example.july.data.DataSource
import com.example.july.data.db.ChatDatabase
import com.example.july.domain.repositories.AuthRepository
import com.example.july.domain.repositories.ChatRepository
import com.example.july.domain.repositories.GroupRepository
import com.example.july.domain.repositories.ProfileRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideChatDatabase(@ApplicationContext context: Context): ChatDatabase {
        return Room.databaseBuilder(
                context,
                ChatDatabase::class.java,
                "app_database"
            ).build()
    }

    @Provides
    fun provideDataSource(chatDatabase: ChatDatabase) : DataSource {
        val firebaseAuth = Firebase.auth
        val firebaseDB = Firebase.database
        return DataSource(firebaseAuth, firebaseDB, chatDatabase)
    }

    @Provides
    fun provideAuthRepository(dataSource : DataSource) : AuthRepository {
        return dataSource
    }

    @Provides
    fun provideProfileRepository(dataSource : DataSource) : ProfileRepository {
        return dataSource
    }

    @Provides
    fun provideChatRepository(dataSource : DataSource) : ChatRepository {
        return dataSource
    }

    @Provides
    fun provideGroupRepository(dataSource : DataSource) : GroupRepository {
        return dataSource
    }

}