package com.easy2mockito.inspection

import com.easy2mockito.quickfix.ReplaceWithMockitoFix
import com.intellij.codeInspection.*
import com.intellij.psi.*

class Easy2MockitoInspection : AbstractBaseJavaLocalInspectionTool() {

    override fun isEnabledByDefault(): Boolean {
        return true
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : JavaElementVisitor() {
            override fun visitMethod(method: PsiMethod) {
                //                addDescriptors(checkMethod(method, holder.getManager(), isOnTheFly));
            }

            override fun visitClass(aClass: PsiClass) {
                //                addDescriptors(checkClass(aClass, holder.getManager(), isOnTheFly));
            }

            override fun visitField(field: PsiField) {
                //                addDescriptors(checkField(field, holder.getManager(), isOnTheFly));
            }

            override fun visitFile(file: PsiFile) {
                //                addDescriptors(checkFile(file, holder.getManager(), isOnTheFly));
            }

            override fun visitMethodCallExpression(expression: PsiMethodCallExpression) {
                if (expression.containingFile.fileType.defaultExtension == "java") {
                    checkMethodCallExpression(expression, holder.manager, isOnTheFly)?.let { holder.registerProblem(it) }
                }
            }

        }
    }

    private fun checkMethodCallExpression(expression: PsiMethodCallExpression, manager: InspectionManager, isOnTheFly: Boolean): ProblemDescriptor? {
        if (expression.text.matches(".*create.*Mock.*".toRegex())) {
            val let = expression.resolveMethod()?.containingClass?.qualifiedName
                    .takeIf { it == "org.easymock.EasyMock" }
                    ?.let { manager.createProblemDescriptor(expression, "Easy2Mockito", ReplaceWithMockitoFix(expression), ProblemHighlightType.GENERIC_ERROR_OR_WARNING, isOnTheFly) }
            return let
        }
        return null
    }
}
