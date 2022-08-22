package com.example.notes.repository.api

import com.example.notes.helper.NetworkConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NotesApiModule {

    @Provides
    fun provideNotesApi(networkConnection: NetworkConnection): NotesApi =
        NotesApiImpl(networkConnection)

}