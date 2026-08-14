package io.github.kafuuneko.asdiff;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

final class ComparisonInputStore {
    private static final String LEFT_TEXT_KEY = "asdiff.leftText";
    private static final String RIGHT_TEXT_KEY = "asdiff.rightText";

    private ComparisonInputStore() {
    }

    static @NotNull ComparisonTexts load(@NotNull Project project) {
        PropertiesComponent properties = PropertiesComponent.getInstance(project);
        return new ComparisonTexts(
                properties.getValue(LEFT_TEXT_KEY, ""),
                properties.getValue(RIGHT_TEXT_KEY, "")
        );
    }

    static void save(@NotNull Project project, @NotNull ComparisonTexts texts) {
        PropertiesComponent properties = PropertiesComponent.getInstance(project);
        properties.setValue(LEFT_TEXT_KEY, texts.left(), "");
        properties.setValue(RIGHT_TEXT_KEY, texts.right(), "");
    }
}
