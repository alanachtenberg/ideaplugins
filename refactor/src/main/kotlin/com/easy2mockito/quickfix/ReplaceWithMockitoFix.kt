package com.easy2mockito.quickfix

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.codeStyle.JavaCodeStyleManager
import org.jetbrains.annotations.Nls

class ReplaceWithMockitoFix(originalExpression: PsiMethodCallExpression) : LocalQuickFix {
    private val originalExpressionPointer = SmartPointerManager.createPointer(originalExpression)


    @Nls
    override fun getFamilyName(): String {
        return "Replace with $MOCK_METHOD"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val elementFactory = JavaPsiFacade.getElementFactory(project)
        val replacement = elementFactory.createExpressionFromText(MOCK_METHOD, null)
        val methodCallExpression = originalExpressionPointer.element
        if (methodCallExpression != null) {
            methodCallExpression.methodExpression.replace(replacement)
            val styleManager = JavaCodeStyleManager.getInstance(project)
            styleManager.shortenClassReferences(methodCallExpression)
            originalExpressionPointer.containingFile?.let { styleManager.optimizeImports(it) }
        }
    }

    companion object {
        private const val MOCK_METHOD = "org.mockito.Mockito.mock"
    }
}
