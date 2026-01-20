package cash.turbine.circom.docs

import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.utils.keyboard
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer
import java.io.File

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EditorFeaturesTest : BaseScreenshotTest() {

    private val fixturesPath = "screenshot-tests/fixtures"

    @Test
    @org.junit.jupiter.api.Order(1)
    fun captureBracketMatching() {
        openFile("$fixturesPath/brackets.circom")

        val gifFile = File(outputDir, "editor-bracket-matching.gif")

        GifCaptureUtil.captureGif(
            outputPath = gifFile.absolutePath,
            durationSeconds = 4,
            fps = 15
        ) {
            // Move cursor to different bracket positions
            with(robot) {
                val editor = find<ComponentFixture>(byXpath("//div[@class='EditorComponentImpl']"))
                editor.click()

                // Navigate through brackets
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_G) // Go to line
                }
                Thread.sleep(200)
                keyboard { enterText("8") }
                keyboard { enter() }
                Thread.sleep(500)

                // Move cursor to bracket
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_CLOSE_BRACKET)
                }
                Thread.sleep(800)

                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_OPEN_BRACKET)
                }
                Thread.sleep(800)
            }
        }

        screenshots.add(
            ScreenshotMetadata(
                id = "editor-bracket-matching",
                filename = "editor-bracket-matching.gif",
                type = "animated",
                feature = "Editor Features",
                width = 1600,
                height = 900,
                displayWidth = 800,
                displayHeight = 450,
                theme = "Darcula",
                ide = "IntelliJ IDEA",
                description = "Bracket matching navigation animation",
                fps = 15,
                durationMs = 4000
            )
        )

        closeAllTabs()
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    fun captureCodeFolding() {
        openFile("$fixturesPath/folding.circom")

        val gifFile = File(outputDir, "editor-code-folding.gif")

        GifCaptureUtil.captureGif(
            outputPath = gifFile.absolutePath,
            durationSeconds = 5,
            fps = 15
        ) {
            with(robot) {
                val editor = find<ComponentFixture>(byXpath("//div[@class='EditorComponentImpl']"))
                editor.click()

                // Collapse all
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_MINUS)
                }
                Thread.sleep(1000)

                // Expand all
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_EQUALS)
                }
                Thread.sleep(1000)

                // Fold current block
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_MINUS)
                }
                Thread.sleep(800)

                // Unfold current block
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_EQUALS)
                }
                Thread.sleep(800)
            }
        }

        screenshots.add(
            ScreenshotMetadata(
                id = "editor-code-folding",
                filename = "editor-code-folding.gif",
                type = "animated",
                feature = "Editor Features",
                width = 1600,
                height = 900,
                displayWidth = 800,
                displayHeight = 450,
                theme = "Darcula",
                ide = "IntelliJ IDEA",
                description = "Code folding expand/collapse animation",
                fps = 15,
                durationMs = 5000
            )
        )

        closeAllTabs()
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    fun captureCommenting() {
        openFile("$fixturesPath/syntax-keywords.circom")

        val gifFile = File(outputDir, "editor-commenting.gif")

        GifCaptureUtil.captureGif(
            outputPath = gifFile.absolutePath,
            durationSeconds = 3,
            fps = 15
        ) {
            with(robot) {
                val editor = find<ComponentFixture>(byXpath("//div[@class='EditorComponentImpl']"))
                editor.click()

                // Go to line 5
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_G)
                }
                Thread.sleep(200)
                keyboard { enterText("5") }
                keyboard { enter() }
                Thread.sleep(300)

                // Toggle comment
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_SLASH)
                }
                Thread.sleep(700)

                // Toggle again to uncomment
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_SLASH)
                }
                Thread.sleep(700)
            }
        }

        screenshots.add(
            ScreenshotMetadata(
                id = "editor-commenting",
                filename = "editor-commenting.gif",
                type = "animated",
                feature = "Editor Features",
                width = 1600,
                height = 900,
                displayWidth = 800,
                displayHeight = 450,
                theme = "Darcula",
                ide = "IntelliJ IDEA",
                description = "Toggle line comment animation",
                fps = 15,
                durationMs = 3000
            )
        )

        closeAllTabs()
    }
}
