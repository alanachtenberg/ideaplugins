import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages

class HelloAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        // TODO: insert action logic here
        val project = e.project
        Messages.showMessageDialog(project, "Hello World", "Greeting", Messages.getInformationIcon())
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)

        var visible = false
        if (project != null && editor != null && editor.selectionModel.hasSelection()) {
            visible = true
        }

        e.presentation.isVisible = visible
    }
}
