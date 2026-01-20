package cash.turbine.circom.docs

import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.utils.keyboard
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer
import java.io.File

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class RefactoringTest : BaseScreenshotTest() {

    private val fixturesPath = "screenshot-tests/fixtures"

    @Test
    @org.junit.jupiter.api.Order(1)
    fun captureRename() {
        openFile("$fixturesPath/rename.circom")

        val gifFile = File(outputDir, "refactoring-rename.gif")

        GifCaptureUtil.captureGif(
            outputPath = gifFile.absolutePath,
            durationSeconds = 5,
            fps = 15
        ) {
            with(robot) {
                val editor = find<ComponentFixture>(byXpath("//div[@class='EditorComponentImpl']"))
                editor.click()

                // Go to line with template name
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_G)
                }
                Thread.sleep(200)
                keyboard { enterText("3") }
                keyboard { enter() }
                Thread.sleep(300)

                // Position on "OriginalName" identifier
                keyboard {
                    repeat(9) {
                        hotKey(java.awt.event.KeyEvent.VK_RIGHT)
                    }
                }
                Thread.sleep(300)

                // Double-click to select word
                // (In actual test, use proper selection)
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_W)
                }
                Thread.sleep(300)

                // Trigger Rename refactoring
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_F6)
                }
                Thread.sleep(800)

                // Type new name
                keyboard { enterText("RenamedTemplate") }
                Thread.sleep(1000)

                // Cancel to avoid actual changes
                keyboard { escape() }
                Thread.sleep(500)
            }
        }

        screenshots.add(
            ScreenshotMetadata(
                id = "refactoring-rename",
                filename = "refactoring-rename.gif",
                type = "animated",
                feature = "Refactoring",
                width = 1600,
                height = 900,
                displayWidth = 800,
                displayHeight = 450,
                theme = "Darcula",
                ide = "IntelliJ IDEA",
                description = "In-place rename refactoring animation",
                fps = 15,
                durationMs = 5000
            )
        )

        closeAllTabs()
    }
}
