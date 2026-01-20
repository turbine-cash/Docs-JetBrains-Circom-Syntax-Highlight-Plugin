package cash.turbine.circom.docs

import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.utils.keyboard
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StructureViewTest : BaseScreenshotTest() {

    private val fixturesPath = "screenshot-tests/fixtures"

    @Test
    @org.junit.jupiter.api.Order(1)
    fun captureStructureView() {
        openFile("$fixturesPath/structure-view.circom")

        with(robot) {
            // Open Structure tool window
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_7)
            }
            Thread.sleep(800)

            // Expand all nodes in structure view
            try {
                val structureView = find<ComponentFixture>(
                    byXpath("//div[@class='StructureViewComponent']"),
                    java.time.Duration.ofSeconds(5)
                )

                // Click to focus and expand
                structureView.click()
                keyboard {
                    hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_EQUALS)
                }
                Thread.sleep(500)
            } catch (e: Exception) {
                // Structure view might have different layout
            }

            Thread.sleep(500)
        }

        captureScreenshot(
            id = "structure-view-expanded",
            feature = "Structure View",
            description = "Structure view showing file hierarchy with templates, signals, vars, and components"
        )

        // Close structure view
        robot.keyboard {
            hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_7)
        }
        Thread.sleep(300)
        closeAllTabs()
    }
}
