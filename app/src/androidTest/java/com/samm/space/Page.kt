package com.samm.space

interface Page {
    fun pressBackButton(): Page
    fun isDisplayed(tag: String): Page
}