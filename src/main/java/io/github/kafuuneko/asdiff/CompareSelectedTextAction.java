package io.github.kafuuneko.asdiff;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public final class CompareSelectedTextAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        String selectedText = EditorSelectionSupport.getSelectedText(event);
        if (project == null || selectedText == null || selectedText.isEmpty()) {
            return;
        }

        ComparisonTexts initialTexts = ComparisonInputStore.load(project).withLeft(selectedText);
        TextComparisonWorkflow.open(project, initialTexts);
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        event.getPresentation().setEnabledAndVisible(EditorSelectionSupport.hasSelection(event));
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }
}
