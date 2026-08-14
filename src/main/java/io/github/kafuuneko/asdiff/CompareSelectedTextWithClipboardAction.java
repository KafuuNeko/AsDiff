package io.github.kafuuneko.asdiff;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;

public final class CompareSelectedTextWithClipboardAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        String selectedText = EditorSelectionSupport.getSelectedText(event);
        String clipboardText = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor);
        if (project == null || selectedText == null || selectedText.isEmpty() || clipboardText == null) {
            return;
        }

        TextComparisonWorkflow.open(project, new ComparisonTexts(selectedText, clipboardText));
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        boolean hasSelection = EditorSelectionSupport.hasSelection(event);
        boolean hasTextClipboard = CopyPasteManager.getInstance()
                .areDataFlavorsAvailable(DataFlavor.stringFlavor);
        event.getPresentation().setVisible(hasSelection);
        event.getPresentation().setEnabled(hasSelection && hasTextClipboard);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }
}
