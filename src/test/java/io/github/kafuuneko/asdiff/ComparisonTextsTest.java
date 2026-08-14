package io.github.kafuuneko.asdiff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComparisonTextsTest {
    @Test
    void swappedReturnsTextsInReverseOrder() {
        ComparisonTexts texts = new ComparisonTexts("before", "after");

        assertEquals(new ComparisonTexts("after", "before"), texts.swapped());
    }

    @Test
    void swappingTwiceRestoresOriginalTexts() {
        ComparisonTexts texts = new ComparisonTexts("left\ntext", "right\ntext");

        assertEquals(texts, texts.swapped().swapped());
    }

    @Test
    void withLeftPreservesRightText() {
        ComparisonTexts texts = new ComparisonTexts("old", "right");

        assertEquals(new ComparisonTexts("selected", "right"), texts.withLeft("selected"));
    }

    @Test
    void formattedJsonFormatsBothSides() {
        ComparisonTexts texts = new ComparisonTexts("{\"left\":1}", "[true,false]");

        assertEquals(
                new ComparisonTexts("{\n  \"left\": 1\n}", "[\n  true,\n  false\n]"),
                texts.formattedJson()
        );
    }
}
