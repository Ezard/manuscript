package io.ezard.manuscript.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ManuscriptControl(val type: KClass<*>)
