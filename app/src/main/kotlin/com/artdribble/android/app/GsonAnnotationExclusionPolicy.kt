package com.artdribble.android.app

import com.artdribble.android.utils.GsonExclude
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class GsonAnnotationExclusionPolicy : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false
    override fun shouldSkipField(f: FieldAttributes?): Boolean = f?.getAnnotation(GsonExclude::class.java) != null
}