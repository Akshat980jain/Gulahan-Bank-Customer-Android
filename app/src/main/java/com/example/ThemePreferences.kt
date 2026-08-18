package com.example

import kotlinx.coroutines.flow.MutableStateFlow

object ThemePreferences {
    val isDarkMode = MutableStateFlow<Boolean?>(null) // null = system default
}
