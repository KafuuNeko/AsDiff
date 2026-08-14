package io.github.kafuuneko.asdiff;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EditorSelectionSupport {
    private EditorSelectionSupport() {
    }

    static boolean hasSelection(@NotNull AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        return event.getProject() != null
                && editor != null
                && editor.getSelectionModel().hasSelection();
    }

    static @Nullable String getSelectedText(@NotNull AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        return editor == null ? null : editor.getSelectionModel().getSelectedText();
    }
}
