package com.example.manytomanytest.utils.extensions

import androidx.core.graphics.toColorInt

fun String.toColorIntSafe() = try {
    toColorInt()
} catch (_: Throwable) {
    null
}