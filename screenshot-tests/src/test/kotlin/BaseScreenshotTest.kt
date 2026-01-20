package cash.turbine.circom.docs

import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.fixtures.CommonContainerFixture
import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.stepsProcessing.step
import com.intellij.remoterobot.utils.keyboard
import com.intellij.remoterobot.utils.waitFor
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.awt.image.BufferedImage
import java.io.File
import java.time.Duration
import javax.imageio.ImageIO

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseScreenshotTest {

    companion object {
        const val ROBOT_HOST = "127.0.0.1"
        const val ROBOT_PORT = 8082
        const val SCALE_FACTOR = 2 // 2x for retina

        val outputDir: File by lazy {
            val dir = File(System.getProperty("screenshot.output.dir", "output/images"))
            dir.mkdirs()
            dir
        }

        val manifestFile: File by lazy {
            File(System.getProperty("screenshot.manifest", "output/screenshots.json"))
        }

        val screenshots = mutableListOf<ScreenshotMetadata>()
    }

    protected lateinit var robot: RemoteRobot

    @BeforeAll
    fun setupRobot() {
        val host = System.getProperty("robot.host", ROBOT_HOST)
        val port = System.getProperty("robot.port", ROBOT_PORT.toString()).toInt()
        robot = RemoteRobot("http://$host:$port")
    }

    protected fun captureScreenshot(
        id: String,
        feature: String,
        description: String = ""
    ): File = step("Capture screenshot: $id") {
        Thread.sleep(500) // Allow UI to settle

        val screenshot = robot.getScreenshot()
        val filename = "$id.png"
        val file = File(outputDir, filename)

        ImageIO.write(screenshot, "png", file)

        screenshots.add(
            ScreenshotMetadata(
                id = id,
                filename = filename,
                type = "static",
                feature = feature,
                width = screenshot.width,
                height = screenshot.height,
                displayWidth = screenshot.width / SCALE_FACTOR,
                displayHeight = screenshot.height / SCALE_FACTOR,
                theme = "Darcula",
                ide = "IntelliJ IDEA",
                description = description
            )
        )

        file
    }

    protected fun openFile(filePath: String) = step("Open file: $filePath") {
        with(robot) {
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_O)
            }
            Thread.sleep(500)

            find<ComponentFixture>(byXpath("//div[@class='SearchTextField']")).apply {
                click()
                keyboard { enterText(filePath) }
            }
            Thread.sleep(300)

            keyboard { enter() }
            waitForFileOpen()
        }
    }

    protected fun waitForFileOpen() = step("Wait for file to open") {
        waitFor(Duration.ofSeconds(10)) {
            try {
                robot.find<ComponentFixture>(byXpath("//div[@class='EditorComponentImpl']"))
                true
            } catch (e: Exception) {
                false
            }
        }
        Thread.sleep(500) // Extra time for syntax highlighting
    }

    protected fun waitForIndexing() = step("Wait for indexing") {
        waitFor(Duration.ofSeconds(60)) {
            try {
                robot.find<ComponentFixture>(
                    byXpath("//div[contains(@text, 'Indexing')]"),
                    Duration.ofSeconds(1)
                )
                false
            } catch (e: Exception) {
                true
            }
        }
    }

    protected fun triggerAction(actionId: String) = step("Trigger action: $actionId") {
        with(robot) {
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_A)
            }
            Thread.sleep(300)

            find<ComponentFixture>(byXpath("//div[@class='SearchTextField']")).apply {
                click()
                keyboard { enterText(actionId) }
            }
            Thread.sleep(300)

            keyboard { enter() }
            Thread.sleep(500)
        }
    }

    protected fun closeAllTabs() = step("Close all tabs") {
        with(robot) {
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_W)
            }
            Thread.sleep(300)
        }
    }

    data class ScreenshotMetadata(
        val id: String,
        val filename: String,
        val type: String,
        val feature: String,
        val width: Int,
        val height: Int,
        val displayWidth: Int,
        val displayHeight: Int,
        val theme: String,
        val ide: String,
        val description: String = "",
        val fps: Int? = null,
        val durationMs: Int? = null
    )
}
