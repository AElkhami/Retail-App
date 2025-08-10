package com.elkhami.retailapp.logging

import timber.log.Timber

class PrefixedDebugTree(
    private val prefix: String
) : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        val defaultTag = super.createStackElementTag(element)
        return "$prefix-$defaultTag"
    }
}