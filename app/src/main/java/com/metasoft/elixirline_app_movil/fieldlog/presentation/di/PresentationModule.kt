package com.metasoft.elixirline_app_movil.fieldlog.presentation.di

import com.metasoft.elixirline_app_movil.fieldlog.data.di.DataModule
import com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel.FieldLogHistoryViewModel
import com.metasoft.elixirline_app_movil.fieldlog.presentation.viewmodel.FieldLogViewModel
import com.metasoft.elixirline_app_movil.fieldlog.util.CloudinaryUploader
import android.content.Context

object PresentationModule {
    fun getFieldLogViewModel(context: Context): FieldLogViewModel {
        return FieldLogViewModel(
            fieldLogService = DataModule.getFieldLogService(),
            cloudinaryUploader = CloudinaryUploader(context)
        )
    }

    fun getFieldLogHistoryViewModel(): FieldLogHistoryViewModel {
        return FieldLogHistoryViewModel(DataModule.getFieldLogService())
    }
}
