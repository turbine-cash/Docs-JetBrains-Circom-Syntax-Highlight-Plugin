package cash.turbine.circom.docs

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManifestGenerator : BaseScreenshotTest() {

    @AfterAll
    fun generateManifest() {
        val manifest = buildString {
            appendLine("{")
            appendLine("  \"generated\": \"${DateTimeFormatter.ISO_INSTANT.format(Instant.now())}\",")
            appendLine("  \"screenshots\": [")

            screenshots.forEachIndexed { index, meta ->
                appendLine("    {")
                appendLine("      \"id\": \"${meta.id}\",")
                appendLine("      \"filename\": \"${meta.filename}\",")
                appendLine("      \"type\": \"${meta.type}\",")
                appendLine("      \"feature\": \"${meta.feature}\",")
                appendLine("      \"width\": ${meta.width},")
                appendLine("      \"height\": ${meta.height},")
                appendLine("      \"displayWidth\": ${meta.displayWidth},")
                appendLine("      \"displayHeight\": ${meta.displayHeight},")
                appendLine("      \"theme\": \"${meta.theme}\",")
                appendLine("      \"ide\": \"${meta.ide}\"")
                if (meta.fps != null) {
                    appendLine("      ,\"fps\": ${meta.fps}")
                }
                if (meta.durationMs != null) {
                    appendLine("      ,\"durationMs\": ${meta.durationMs}")
                }
                if (meta.description.isNotEmpty()) {
                    appendLine("      ,\"description\": \"${meta.description}\"")
                }
                if (index < screenshots.size - 1) {
                    appendLine("    },")
                } else {
                    appendLine("    }")
                }
            }

            appendLine("  ]")
            appendLine("}")
        }

        manifestFile.parentFile?.mkdirs()
        manifestFile.writeText(manifest)

        println("Generated manifest with ${screenshots.size} screenshots at: ${manifestFile.absolutePath}")
    }
}
