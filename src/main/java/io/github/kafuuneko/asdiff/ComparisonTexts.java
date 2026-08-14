package io.github.kafuuneko.asdiff;

import org.jetbrains.annotations.NotNull;

record ComparisonTexts(@NotNull String left, @NotNull String right) {
    @NotNull ComparisonTexts withLeft(@NotNull String newLeft) {
        return new ComparisonTexts(newLeft, right);
    }

    ComparisonTexts swapped() {
        return new ComparisonTexts(right, left);
    }

    @NotNull ComparisonTexts formattedJson() {
        return new ComparisonTexts(
                JsonTextFormatter.formatIfJson(left),
                JsonTextFormatter.formatIfJson(right)
        );
    }
}
