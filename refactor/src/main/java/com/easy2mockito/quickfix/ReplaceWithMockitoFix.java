package com.easy2mockito.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ReplaceWithMockitoFix implements LocalQuickFix {
    private static final String MOCK_METHOD = "org.mockito.Mockito.mock";
    private final SmartPsiElementPointer<PsiMethodCallExpression> originalExpressionPointer;

    public ReplaceWithMockitoFix(PsiMethodCallExpression originalExpression) {
        this.originalExpressionPointer = SmartPointerManager.createPointer(originalExpression);
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
        PsiMethodCallExpression methodCallExpression = originalExpressionPointer.getElement();
        if (methodCallExpression != null) {
            methodCallExpression.getMethodExpression().replace(replacement);
            JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(project);
            styleManager.shortenClassReferences(methodCallExpression);
            styleManager.optimizeImports(Objects.requireNonNull(originalExpressionPointer.getContainingFile()));
        }
    }
}
