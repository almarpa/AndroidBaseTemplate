package com.example.androidtemplateapp.entity

import com.example.androidtemplateapp.entity.enums.AppThemeEnum

data class UserData(
    val locale: String,
    val theme: AppThemeEnum,
)
