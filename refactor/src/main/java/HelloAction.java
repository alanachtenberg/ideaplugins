import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class HelloAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        Messages.showMessageDialog(project, "Hello World", "Greeting", Messages.getInformationIcon());
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        boolean visible = false;
        if (project != null && editor != null && editor.getSelectionModel().hasSelection()) {
            visible = true;
        }

        e.getPresentation().setVisible(visible);
    }
}
