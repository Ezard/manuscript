@file:Suppress("TestFunctionName")

package io.ezard.manuscript.ksp.testing

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.ezard.manuscript.ksp.ManuscriptSymbolProcessorProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.streams.toList

class ManuscriptProcessorTest {
    private val projectDirAbsolutePath = Paths
        .get("").toAbsolutePath()
        .toString()
    private val resourcesPath = Paths
        .get(projectDirAbsolutePath, "/src/test/resources")
        .toAbsolutePath()
        .toString()

    private fun getSources(directory: Path) = Files
        .list(directory)
        .filter { path -> path.name.startsWith("Input_") }
        .map { path -> kotlin(path.name.removePrefix("Input_"), path.readText()) }

    private fun getExpectedOutputs(directory: Path, kspSourcesDir: Path) = Files
        .list(directory)
        .filter { path -> path.name.startsWith("Output_") }
        .map { path ->
            val packageName = path.readLines().first().replace("package ", "")
            val outputPath = kspSourcesDir
                .resolve(packageName.replace('.', File.separatorChar))
                .resolve(path.name.removePrefix("Output_"))
            outputPath to path.readText()
        }

    private fun getExpectedError(directory: Path) = Files
        .list(directory)
        .filter { path -> path.name == "Error.txt" }
        .map(Path::readText)
        .toList()
        .firstOrNull()

    @TestFactory
    fun createTests(): List<DynamicTest> {
        return Files.list(Paths.get(resourcesPath)).map { directory ->
            val testName = directory.name.replace('_', ' ')
            val sourceFiles = getSources(directory).toList()

            DynamicTest.dynamicTest(testName) {
                val compilation = KotlinCompilation().apply {
                    symbolProcessorProviders = listOf(ManuscriptSymbolProcessorProvider())
                    sources = sourceFiles
                    inheritClassPath = true
                }
                val outputs = getExpectedOutputs(
                    directory,
                    compilation.kspSourcesDir.toPath().resolve("kotlin"),
                )
                val expectedError = getExpectedError(directory)

                val result = compilation.compile()

                if (expectedError == null) {
                    assertEquals(ExitCode.OK, result.exitCode)
                    outputs.forEach { (path, expected) ->
                        assertEquals(
                            expected.trim().lines().joinToString(System.lineSeparator()),
                            path.readText().trim().lines().joinToString(System.lineSeparator()),
                        )
                    }
                } else {
                    assertEquals(ExitCode.COMPILATION_ERROR, result.exitCode)
                    val errorMessagePrinted = result.messages.lines().any { line ->
                        line.contains("io.ezard.manuscript.ksp.ManuscriptCodeGenException: The class passed to @ManuscriptControl must match the type passed as the generic to the Control parameter (StringControl.kt:9)")
                    }
                    assertTrue(errorMessagePrinted)
                }
            }
        }.toList()
    }
}
