package io.github.kafuuneko.asdiff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTextFormatterTest {
    @Test
    void formatsJsonObjectsAndArrays() {
        String input = "{\"name\":\"AsDiff\",\"values\":[1,2]}";
        String expected = """
                {
                  "name": "AsDiff",
                  "values": [
                    1,
                    2
                  ]
                }""";

        assertEquals(expected, JsonTextFormatter.formatIfJson(input));
    }

    @Test
    void leavesInvalidJsonUnchanged() {
        String input = "{name: AsDiff}";

        assertEquals(input, JsonTextFormatter.formatIfJson(input));
    }

    @Test
    void leavesJsonWithDuplicateKeysUnchanged() {
        String input = "{\"value\":1,\"value\":2}";

        assertEquals(input, JsonTextFormatter.formatIfJson(input));
    }

    @Test
    void leavesJsonWithTrailingContentUnchanged() {
        String input = "{\"value\":1} trailing";

        assertEquals(input, JsonTextFormatter.formatIfJson(input));
    }

    @Test
    void leavesPlainTextAndJsonScalarsUnchanged() {
        assertEquals("plain text", JsonTextFormatter.formatIfJson("plain text"));
        assertEquals("true", JsonTextFormatter.formatIfJson("true"));
    }
}
