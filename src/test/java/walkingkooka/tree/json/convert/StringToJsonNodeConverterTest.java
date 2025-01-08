/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.json.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;

public final class StringToJsonNodeConverterTest implements ConverterTesting2<StringToJsonNodeConverter<FakeJsonNodeConverterContext>, FakeJsonNodeConverterContext>,
    ToStringTesting<StringToJsonNodeConverter<FakeJsonNodeConverterContext>> {

    @Test
    public void testConvertStringToJsonNodeWithBadJsonFails() {
        this.convertFails(
            "bad json",
            JsonNode.class
        );
    }

    @Test
    public void testConvertStringToJsonNode() {
        final String json = "{}";

        this.convertStringAndCheck(
            json,
            JsonNode.class,
            JsonNode.parse(json)
        );
    }

    @Test
    public void testConvertStringToJsonBoolean() {
        this.convertStringAndCheck(
            JsonNode.booleanNode(true)
        );
    }

    @Test
    public void testConvertStringToJsonNull() {
        this.convertStringAndCheck(
            JsonNode.nullNode()
        );
    }

    @Test
    public void testConvertStringToJsonNumber() {
        this.convertStringAndCheck(
            JsonNode.number(1)
        );
    }

    @Test
    public void testConvertStringToJsonString() {
        this.convertStringAndCheck(
            JsonNode.string("Abc123")
        );
    }

    @Test
    public void testConvertStringToJsonArray() {
        this.convertStringAndCheck(
            JsonNode.array()
                .appendChild(JsonNode.booleanNode(true))
                .appendChild(JsonNode.nullNode())
                .appendChild(JsonNode.number(123))
        );
    }

    @Test
    public void testConvertStringToJsonObject() {
        this.convertStringAndCheck(
            JsonNode.object()
                .set(JsonPropertyName.with("field"), JsonNode.booleanNode(true))
        );
    }

    private <T extends JsonNode> void convertStringAndCheck(final JsonNode json) {
        this.convertAndCheck(
            json.toString(),
            json
        );
    }

    private <T extends JsonNode> void convertStringAndCheck(final String json,
                                                            final Class<T> type,
                                                            final T expected) {
        this.convertAndCheck(
            json,
            type,
            expected
        );
    }

    @Override
    public StringToJsonNodeConverter<FakeJsonNodeConverterContext> createConverter() {
        return StringToJsonNodeConverter.instance();
    }

    @Override
    public FakeJsonNodeConverterContext createContext() {
        return new FakeJsonNodeConverterContext();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            StringToJsonNodeConverter.instance(),
            StringToJsonNodeConverter.class.getSimpleName()
        );
    }

    // class............................................................................................................

    @Override
    public Class<StringToJsonNodeConverter<FakeJsonNodeConverterContext>> type() {
        return Cast.to(StringToJsonNodeConverter.class);
    }
}
