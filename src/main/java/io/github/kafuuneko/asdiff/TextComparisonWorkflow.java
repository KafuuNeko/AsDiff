package io.github.kafuuneko.asdiff;

import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffManager;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

final class TextComparisonWorkflow {
    private TextComparisonWorkflow() {
    }

    static void open(@NotNull Project project, @NotNull ComparisonTexts initialTexts) {
        TextComparisonDialog dialog = new TextComparisonDialog(project, initialTexts.formattedJson());
        if (!dialog.showAndGet()) {
            return;
        }

        ComparisonTexts texts = dialog.getResult();
        if (texts == null) {
            return;
        }

        DiffContentFactory contentFactory = DiffContentFactory.getInstance();
        SimpleDiffRequest request = new SimpleDiffRequest(
                "Text Comparison",
                contentFactory.create(project, texts.left(), PlainTextFileType.INSTANCE),
                contentFactory.create(project, texts.right(), PlainTextFileType.INSTANCE),
                "Left",
                "Right"
        );
        DiffManager.getInstance().showDiff(project, request);
    }
}
