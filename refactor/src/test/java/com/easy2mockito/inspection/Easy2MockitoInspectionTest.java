package com.easy2mockito.inspection;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.Assert;

import java.io.File;

public class Easy2MockitoInspectionTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        File testData = new File("build/resources/test/testdata/Easy2MockitoInspection");
        return testData.getAbsolutePath();
    }

    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new TestProjectDescriptor();
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.copyDirectoryToProject("", "");
        myFixture.configureByFile("input/Test.java");
        //noinspection unchecked
        myFixture.enableInspections(Easy2MockitoInspection.class);
    }

    public void testQuickFix() {
        Editor editor = myFixture.getEditor();
        CaretModel caretModel = editor.getCaretModel();

        //TODO there must be a better way
        caretModel.moveToOffset(129);
        assertQuickFixNotAvailable("Replace with");
        caretModel.moveToOffset(155);
        assertQuickFixNotAvailable("Replace with");
        caretModel.moveToOffset(176);
        assertQuickFixNotAvailable("Replace with");

        caretModel.moveToOffset(230);
        IntentionAction quickFix = myFixture.findSingleIntention("Replace with");
        myFixture.launchAction(quickFix);
        caretModel.moveToOffset(271);
        quickFix = myFixture.findSingleIntention("Replace with");
        myFixture.launchAction(quickFix);

        myFixture.checkResultByFile("output/Test.java", true);
    }

    private void assertQuickFixNotAvailable(String hint) {
        IntentionAction value = myFixture.getAvailableIntentions().stream().filter(i -> i.getText().startsWith(hint)).findAny().orElse(null);
        Assert.assertNull("unexpected quick fix found", value);
    }
}