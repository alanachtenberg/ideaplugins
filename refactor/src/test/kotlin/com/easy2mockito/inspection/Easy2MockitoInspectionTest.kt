package com.easy2mockito.inspection

import com.intellij.testFramework.LightProjectDescriptor
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Assert
import java.io.File

class Easy2MockitoInspectionTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        val testData = File("build/resources/test/testdata/Easy2MockitoInspection")
        return testData.absolutePath
    }

    override fun getProjectDescriptor(): LightProjectDescriptor {
        return TestProjectDescriptor()
    }

    @Throws(Throwable::class)
    override fun runTest() {
        super.runTest()
    }

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        myFixture.copyDirectoryToProject("", "")
        myFixture.configureByFile("input/Test.java")

        myFixture.enableInspections(Easy2MockitoInspection::class.java)
    }

    fun testQuickFix() {
        val editor = myFixture.editor
        val caretModel = editor.caretModel

        //TODO there must be a better way
        caretModel.moveToOffset(129)
        assertQuickFixNotAvailable("Replace with")
        caretModel.moveToOffset(155)
        assertQuickFixNotAvailable("Replace with")
        caretModel.moveToOffset(176)
        assertQuickFixNotAvailable("Replace with")

        caretModel.moveToOffset(230)
        var quickFix = myFixture.findSingleIntention("Replace with")
        myFixture.launchAction(quickFix)
        caretModel.moveToOffset(271)
        quickFix = myFixture.findSingleIntention("Replace with")
        myFixture.launchAction(quickFix)

        myFixture.checkResultByFile("output/Test.java", true)
    }

    private fun assertQuickFixNotAvailable(hint: String) {
        val value = myFixture.availableIntentions.any { i -> i.text.startsWith(hint) }
        Assert.assertFalse("unexpected quick fix found", value)
    }
}