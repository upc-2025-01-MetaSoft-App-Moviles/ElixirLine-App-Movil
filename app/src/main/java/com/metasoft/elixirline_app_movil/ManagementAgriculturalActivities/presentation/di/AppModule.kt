package com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.presentation.di

import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.ApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.remote.FakeApiService
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.NotificationRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.ParcelRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.data.repository.TaskReportRepositoryImpl
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.NotificationRepository
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.ParcelRepository
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.repository.TaskReportRepository
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetNotificationsUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetParcelsUseCase
import com.metasoft.elixirline_app_movil.ManagementAgriculturalActivities.domain.usecase.GetReportsUseCase

@Module
object AppModule {

    private val fakeApiService = FakeApiService()

    @Provides
    fun provideApiService(): ApiService = fakeApiService

    @Provides
    fun provideParcelRepository(api: ApiService): ParcelRepository = ParcelRepositoryImpl(api)

    @Provides
    fun provideTaskReportRepository(api: ApiService): TaskReportRepository = TaskReportRepositoryImpl(api)

    @Provides
    fun provideNotificationRepository(api: ApiService): NotificationRepository = NotificationRepositoryImpl(api)

    @Provides
    fun provideGetParcelsUseCase(repository: ParcelRepository): GetParcelsUseCase =
        GetParcelsUseCase(repository)

    @Provides
    fun provideGetReportsUseCase(repository: TaskReportRepository): GetReportsUseCase =
        GetReportsUseCase(repository)

    @Provides
    fun provideGetNotificationsUseCase(repository: NotificationRepository): GetNotificationsUseCase =
        GetNotificationsUseCase(repository)
}