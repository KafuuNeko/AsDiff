package io.github.kafuuneko.asdiff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.jetbrains.annotations.NotNull;

final class JsonTextFormatter {
    private static final ObjectMapper JSON_MAPPER = JsonMapper.builder()
            .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION)
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
            .build();
    private static final ObjectWriter PRETTY_WRITER = JSON_MAPPER.writer(createPrettyPrinter());

    private JsonTextFormatter() {
    }

    static @NotNull String formatIfJson(@NotNull String text) {
        String candidate = text.strip();
        if (!startsWithContainer(candidate)) {
            return text;
        }

        try {
            JsonNode root = JSON_MAPPER.readTree(candidate);
            if (root == null || (!root.isObject() && !root.isArray())) {
                return text;
            }
            return PRETTY_WRITER.writeValueAsString(root);
        } catch (JsonProcessingException exception) {
            return text;
        }
    }

    private static boolean startsWithContainer(@NotNull String text) {
        return !text.isEmpty() && (text.charAt(0) == '{' || text.charAt(0) == '[');
    }

    private static @NotNull DefaultPrettyPrinter createPrettyPrinter() {
        DefaultIndenter indenter = new DefaultIndenter("  ", "\n");
        Separators separators = Separators.createDefaultInstance()
                .withObjectFieldValueSpacing(Separators.Spacing.AFTER);
        return new DefaultPrettyPrinter(separators)
                .withObjectIndenter(indenter)
                .withArrayIndenter(indenter);
    }
}
