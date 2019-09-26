package com.easy2mockito.inspection;

import com.easy2mockito.quickfix.ReplaceWithMockitoFix;
import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMember;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Easy2MockitoInspection extends AbstractBaseJavaLocalInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
//                addDescriptors(checkMethod(method, holder.getManager(), isOnTheFly));
            }

            @Override
            public void visitClass(PsiClass aClass) {
//                addDescriptors(checkClass(aClass, holder.getManager(), isOnTheFly));
            }

            @Override
            public void visitField(PsiField field) {
//                addDescriptors(checkField(field, holder.getManager(), isOnTheFly));
            }

            @Override
            public void visitFile(PsiFile file) {
//                addDescriptors(checkFile(file, holder.getManager(), isOnTheFly));
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                if (expression.getContainingFile().getFileType().getDefaultExtension().equals("java")) {
                    checkMethodCallExpression(expression, holder.getManager(), isOnTheFly).ifPresent(holder::registerProblem);
                }
            }

        };
    }

    @NotNull
    private Optional<ProblemDescriptor> checkMethodCallExpression(@NotNull PsiMethodCallExpression expression, @NotNull InspectionManager manager, boolean isOnTheFly) {
        if (expression.getText().matches(".*create.*Mock.*")) {
            return Optional.ofNullable(expression.resolveMethod())
                    .map(PsiMember::getContainingClass)
                    .map(PsiClass::getQualifiedName)
                    .filter(name -> name.equals("org.easymock.EasyMock"))
                    .map(name -> manager.createProblemDescriptor(expression, "Easy2Mockito", new ReplaceWithMockitoFix(expression), ProblemHighlightType.WEAK_WARNING, isOnTheFly));
        }
        return Optional.empty();
    }
}
