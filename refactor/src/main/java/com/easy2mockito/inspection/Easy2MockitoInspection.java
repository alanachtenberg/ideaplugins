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
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                    addDescriptors(checkMethodCallExpression(expression, holder.getManager(), isOnTheFly));
                }
            }

            private void addDescriptors(final ProblemDescriptor[] descriptors) {
                if (descriptors != null) {
                    for (ProblemDescriptor descriptor : descriptors) {
                        holder.registerProblem(descriptor);
                    }
                }
            }
        };
    }

    @Nullable
    public ProblemDescriptor[] checkMethodCallExpression(@NotNull PsiMethodCallExpression expression, @NotNull InspectionManager manager, boolean isOnTheFly) {
        if (expression.getText().matches(".*create.*Mock.*")){
            PsiMethod psiMethod = expression.resolveMethod();
            if (psiMethod.getContainingClass().getQualifiedName().equals("org.easymock.EasyMock")) {
                ProblemDescriptor descriptor = manager.createProblemDescriptor(expression, "Easy2Mockito", new ReplaceWithMockitoFix(expression), ProblemHighlightType.WEAK_WARNING, isOnTheFly);
                return new ProblemDescriptor[]{descriptor};
            }
        }
        return null;
    }

}
