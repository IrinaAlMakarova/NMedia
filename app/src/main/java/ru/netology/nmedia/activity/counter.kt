package ru.netology.nmedia.activity

import android.text.Editable

fun counter(count: Int): String {
    val allCount = if (count in 1_000..9_999) {
        "${(count / 100) / 10.0}K"
    } else if (count in 10_000..999_999) {
        "${(count / 1_000)}K"
    } else if (count >= 1_000_000) {
        "${(count / 100_000) / 10.0}M"
    } else {
        "$count"
    }
    return allCount
}