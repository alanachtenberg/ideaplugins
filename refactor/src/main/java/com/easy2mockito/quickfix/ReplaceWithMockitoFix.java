package com.easy2mockito.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class ReplaceWithMockitoFix implements LocalQuickFix {
    private static final String MOCK_METHOD = "org.mockito.Mockito.mock";
    PsiMethodCallExpression originalExpression;

    public ReplaceWithMockitoFix(PsiMethodCallExpression originalExpression) {
        this.originalExpression = originalExpression;
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "Replace with " + MOCK_METHOD;
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
        PsiExpression replacement = elementFactory.createExpressionFromText(MOCK_METHOD, null);
        originalExpression.getMethodExpression().replace(replacement);
        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(project);
        styleManager.shortenClassReferences(originalExpression);
        styleManager.optimizeImports(originalExpression.getContainingFile());
    }
}
