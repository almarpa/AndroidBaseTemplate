package com.example.androidtemplateapp.ui.common.mocks

import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorData
import com.example.androidtemplateapp.common.errorhandler.entity.AppErrorType

fun mockNotFoundAppError() =
    AppError(
        type = AppErrorType.Api.NotFound,
        data = mockAppErrorData(),
        cause = null
    )

fun mockAppErrorData() = AppErrorData(
    "404",
    "Not Found"
)
