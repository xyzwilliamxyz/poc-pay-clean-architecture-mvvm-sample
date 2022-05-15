package com.example.pocpay.archunit

import android.os.Parcelable
import android.util.Log
import com.example.pocpay.di.CoroutineDispatcherProvider
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchUnitRunner
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
import com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
import kotlinx.coroutines.Dispatchers
import org.junit.runner.RunWith


@Suppress("PropertyName", "Unused")
@RunWith(ArchUnitRunner::class)
@AnalyzeClasses(packages = ["com.example.pocpay"])
class CodingRulesTest {

    @ArchTest
    val `ViewModels should be injecting coroutine dispatcher`: ArchRule =
        classes().that().haveNameMatching(".*ViewModel")
            .should().dependOnClassesThat().haveSimpleName(CoroutineDispatcherProvider::class.simpleName)
            .because("it makes viewModels more testable")

    @ArchTest
    val `ViewModels should not be using coroutine dispatcher directly`: ArchRule =
        noClasses().that().haveNameMatching(".*ViewModel")
            .should().accessClassesThat()
            .haveNameMatching(".*${Dispatchers::class.simpleName}")

    @ArchTest
    val `No generic exceptions should be thrown`: ArchRule = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS

    @ArchTest
    val `No android util logging should be used`: ArchRule =
        noClasses().should().accessClassesThat()
            .haveFullyQualifiedName(Log::class.qualifiedName)
            .because("We are using Timber for logging")

    @ArchTest
    val `Response classes should not be implementing Parcelable`: ArchRule =
        noClasses().that().resideInAPackage("..response..")
            .should().implement(Parcelable::class.java)
            .because("They shouldn't be passed to bundles")
}