package io.github.kafuuneko.asdiff;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBSplitter;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

final class TextComparisonDialog extends DialogWrapper {
    private static final String SPLITTER_KEY = "asdiff.inputSplitterProportion";

    private final Project project;
    private final Editor leftEditor;
    private final Editor rightEditor;
    private final Action swapAction = new AbstractAction("Swap") {
        @Override
        public void actionPerformed(ActionEvent event) {
            swapTexts();
        }
    };
    private final Action clearAction = new AbstractAction("Clear") {
        @Override
        public void actionPerformed(ActionEvent event) {
            clearTexts();
        }
    };

    private ComparisonTexts result;

    TextComparisonDialog(@NotNull Project project, @NotNull ComparisonTexts initialTexts) {
        super(project, true);
        this.project = project;

        EditorFactory editorFactory = EditorFactory.getInstance();
        leftEditor = editorFactory.createEditor(
                editorFactory.createDocument(initialTexts.left()), project, PlainTextFileType.INSTANCE, false
        );
        rightEditor = editorFactory.createEditor(
                editorFactory.createDocument(initialTexts.right()), project, PlainTextFileType.INSTANCE, false
        );

        configureEditor(leftEditor, "Paste the original text here");
        configureEditor(rightEditor, "Paste the changed text here");

        setTitle("Compare Text");
        setOKButtonText("Compare");
        setResizable(true);
        setHorizontalStretch(1.5f);
        setVerticalStretch(1.3f);
        init();
    }

    @Override
    protected @NotNull JComponent createCenterPanel() {
        JBSplitter splitter = new JBSplitter(false, SPLITTER_KEY, 0.5f);
        splitter.setFirstComponent(createEditorPanel("Left", leftEditor));
        splitter.setSecondComponent(createEditorPanel("Right", rightEditor));
        splitter.setPreferredSize(JBUI.size(900, 560));
        return splitter;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return leftEditor.getContentComponent();
    }

    @Override
    protected Action @NotNull [] createLeftSideActions() {
        return new Action[]{swapAction, clearAction};
    }

    @Override
    protected void doOKAction() {
        result = currentTexts().formattedJson();
        replaceTexts(result);
        super.doOKAction();
    }

    @Override
    protected void dispose() {
        ComparisonInputStore.save(project, currentTexts());
        EditorFactory editorFactory = EditorFactory.getInstance();
        editorFactory.releaseEditor(leftEditor);
        editorFactory.releaseEditor(rightEditor);
        super.dispose();
    }

    @Nullable ComparisonTexts getResult() {
        return result;
    }

    private static void configureEditor(@NotNull Editor editor, @NotNull String placeholder) {
        EditorSettings settings = editor.getSettings();
        settings.setLineNumbersShown(true);
        settings.setFoldingOutlineShown(false);
        settings.setLineMarkerAreaShown(false);
        settings.setRightMarginShown(false);
        settings.setAdditionalColumnsCount(3);
        settings.setAdditionalLinesCount(2);
        settings.setCaretRowShown(true);

        if (editor instanceof EditorEx editorEx) {
            editorEx.setEmbeddedIntoDialogWrapper(true);
            editorEx.setPlaceholder(placeholder);
            editorEx.setShowPlaceholderWhenFocused(true);
        }
    }

    private static @NotNull JComponent createEditorPanel(@NotNull String title, @NotNull Editor editor) {
        JPanel panel = new JPanel(new BorderLayout(0, JBUI.scale(6)));
        JLabel label = new JLabel(title);
        label.setBorder(JBUI.Borders.emptyLeft(2));
        panel.add(label, BorderLayout.NORTH);
        panel.add(editor.getComponent(), BorderLayout.CENTER);
        panel.setMinimumSize(new Dimension(JBUI.scale(220), JBUI.scale(180)));
        return panel;
    }

    private @NotNull ComparisonTexts currentTexts() {
        return new ComparisonTexts(leftEditor.getDocument().getText(), rightEditor.getDocument().getText());
    }

    private void swapTexts() {
        ComparisonTexts swapped = currentTexts().swapped();
        replaceTexts(swapped);
    }

    private void clearTexts() {
        replaceTexts(new ComparisonTexts("", ""));
        leftEditor.getContentComponent().requestFocusInWindow();
    }

    private void replaceTexts(@NotNull ComparisonTexts texts) {
        ApplicationManager.getApplication().runWriteAction(() -> {
            replaceDocumentText(leftEditor.getDocument(), texts.left());
            replaceDocumentText(rightEditor.getDocument(), texts.right());
        });
    }

    private static void replaceDocumentText(@NotNull Document document, @NotNull String text) {
        document.replaceString(0, document.getTextLength(), text);
    }
}
