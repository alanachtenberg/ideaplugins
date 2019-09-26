package com.easy2mockito.inspection

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.testFramework.LightProjectDescriptor
import com.intellij.testFramework.PsiTestUtil

class TestProjectDescriptor : LightProjectDescriptor() {

    override fun createMainModule(project: Project): Module {
        val mainModule = super.createMainModule(project)
        //TODO load entire folder, forget about version info
        PsiTestUtil.addLibrary(mainModule, "projectRuntimeLibs/easymock-2.0.jar")
        PsiTestUtil.addLibrary(mainModule, "projectRuntimeLibs/mockito-all-1.10.19.jar")
        return mainModule
    }
}
