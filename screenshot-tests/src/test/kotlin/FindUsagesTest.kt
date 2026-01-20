package cash.turbine.circom.docs

import com.intellij.remoterobot.fixtures.ComponentFixture
import com.intellij.remoterobot.search.locators.byXpath
import com.intellij.remoterobot.utils.keyboard
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class FindUsagesTest : BaseScreenshotTest() {

    private val fixturesPath = "screenshot-tests/fixtures"

    @Test
    @org.junit.jupiter.api.Order(1)
    fun captureFindUsages() {
        openFile("$fixturesPath/find-usages.circom")

        with(robot) {
            val editor = find<ComponentFixture>(byXpath("//div[@class='EditorComponentImpl']"))
            editor.click()

            // Go to Hasher template definition
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_META, java.awt.event.KeyEvent.VK_G)
            }
            Thread.sleep(200)
            keyboard { enterText("3") }
            keyboard { enter() }
            Thread.sleep(300)

            // Position on "Hasher" identifier
            keyboard {
                repeat(9) {
                    hotKey(java.awt.event.KeyEvent.VK_RIGHT)
                }
            }
            Thread.sleep(300)

            // Trigger Find Usages
            keyboard {
                hotKey(java.awt.event.KeyEvent.VK_ALT, java.awt.event.KeyEvent.VK_F7)
            }
            Thread.sleep(1000)
        }

        captureScreenshot(
            id = "find-usages-results",
            feature = "Find Usages",
            description = "Find Usages showing all references to Hasher template"
        )

        // Close find usages panel
        robot.keyboard { escape() }
        Thread.sleep(300)
        closeAllTabs()
    }
}
