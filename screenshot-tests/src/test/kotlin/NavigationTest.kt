package cash.turbine.circom.docs

import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.utils.keyboard
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class NavigationTest : BaseScreenshotTest() {

    private val fixturesPath = "screenshot-tests/fixtures"

    @Test
    @org.junit.jupiter.api.Order(1)
    fun captureGoToSymbol() {
        openFile("$fixturesPath/navigation-multi.circom")
        Thread.sleep(500)

        with(robot) {
            // Open Go to Symbol dialog
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_O)
            }
            Thread.sleep(500)

            // Type search query
            keyboard { enterText("Mul") }
            Thread.sleep(800)
        }

        captureScreenshot(
            id = "navigation-goto-symbol",
            feature = "Navigation",
            description = "Go to Symbol popup showing search results for 'Mul'"
        )

        // Close dialog
        robot.keyboard { escape() }
        Thread.sleep(300)
        closeAllTabs()
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    fun captureGoToDefinition() {
        openFile("$fixturesPath/navigation-multi.circom")

        with(robot) {
            val editor = find<ComponentFixture>(byXpath("//div[@class='EditorComponentImpl']"))
            editor.click()

            // Go to line with component usage
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_G)
            }
            Thread.sleep(200)
            keyboard { enterText("51") }
            keyboard { enter() }
            Thread.sleep(500)

            // Position on "Adder" identifier
            keyboard {
                repeat(15) {
                    hotKey(java.awt.event.KeyEvent.VK_RIGHT)
                }
            }
            Thread.sleep(300)
        }

        captureScreenshot(
            id = "navigation-goto-definition",
            feature = "Navigation",
            description = "Cursor positioned on identifier ready for Ctrl+Click navigation"
        )

        closeAllTabs()
    }
}
