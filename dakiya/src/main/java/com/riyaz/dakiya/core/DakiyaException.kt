package com.riyaz.dakiya.core

internal sealed class DakiyaException private constructor(override val message: String): Exception(){
    class RequiredFieldNullException(private val fieldName: String): DakiyaException("$fieldName is null")
    class MinimumApiRequirementException(private val requiredApi: Int): DakiyaException("Minimum required API level for this style is $requiredApi")
    class BuildAfterTimerEndException(override val message: String = "Builder after timer end"): DakiyaException(message)
    class NoStyleFoundException(private val style: String): DakiyaException("No such style found: $style")
    class MetaTagNotFoundException(private val propertyName: String): DakiyaException("No meta tag found for $propertyName")
    class Generic(override val message: String): DakiyaException(message)
}