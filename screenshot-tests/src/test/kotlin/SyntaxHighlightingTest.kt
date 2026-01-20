package cash.turbine.circom.docs

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SyntaxHighlightingTest : BaseScreenshotTest() {

    private val fixturesPath = "screenshot-tests/fixtures"

    @Test
    @org.junit.jupiter.api.Order(1)
    fun captureKeywordsSyntax() {
        openFile("$fixturesPath/syntax-keywords.circom")
        captureScreenshot(
            id = "syntax-highlighting-keywords",
            feature = "Syntax Highlighting",
            description = "Keywords highlighting: template, signal, var, component, function"
        )
        closeAllTabs()
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    fun captureSignalsSyntax() {
        openFile("$fixturesPath/syntax-signals.circom")
        captureScreenshot(
            id = "syntax-highlighting-signals",
            feature = "Syntax Highlighting",
            description = "Signal type highlighting: input and output keywords in blue-violet"
        )
        closeAllTabs()
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    fun captureConstraintsSyntax() {
        openFile("$fixturesPath/syntax-constraints.circom")
        captureScreenshot(
            id = "syntax-highlighting-constraints",
            feature = "Syntax Highlighting",
            description = "Constraint operator highlighting: ===, <==, ==>, <--, --> in teal"
        )
        closeAllTabs()
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    fun captureModifiersSyntax() {
        openFile("$fixturesPath/syntax-modifiers.circom")
        captureScreenshot(
            id = "syntax-highlighting-modifiers",
            feature = "Syntax Highlighting",
            description = "Modifier highlighting: parallel, custom, bus in yellow"
        )
        closeAllTabs()
    }
}
