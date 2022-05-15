package com.example.pocpay.archunit

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
class LayerDependencyRulesTest {

    @ArchTest
    val `Data classes should only be accessed on data package`: ArchRule =
        classes().that().resideInAnyPackage("..entity..", "..dao..", "..response..", "..api..")
            .should().onlyBeAccessed().byAnyPackage("..data..")

    @ArchTest
    val `Repositories should not be accessed by view models`: ArchRule =
        noClasses().that().resideInAPackage("..repository..")
            .should().onlyBeAccessed().byAnyPackage("..viewModel..")
}