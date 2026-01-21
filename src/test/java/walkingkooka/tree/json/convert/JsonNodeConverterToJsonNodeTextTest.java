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
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.net.email.EmailAddress;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;

import java.util.Optional;

public final class JsonNodeConverterToJsonNodeTextTest extends JsonNodeConverterTestCase<JsonNodeConverterToJsonNodeText<FakeJsonNodeConverterContext>, FakeJsonNodeConverterContext> {

    @Test
    public void testConvertToJsonNodeFails() {
        this.convertFails(
            "{}",
            JsonNode.class
        );
    }

    @Test
    public void testConvertEmailAddressToString() {
        final EmailAddress emailAddress = EmailAddress.parse("test@example.com");

        this.convertAndCheck(
            emailAddress,
            String.class,
            JsonNodeMarshallContexts.basic()
                .marshall(emailAddress)
                .toString()
        );
    }

    @Test
    public void testConvertEmailAddressToCharSequence() {
        final EmailAddress emailAddress = EmailAddress.parse("test@example.com");

        this.convertAndCheck(
            emailAddress,
            CharSequence.class,
            JsonNodeMarshallContexts.basic()
                .marshall(emailAddress)
                .toString()
        );
    }

    @Test
    public void testConvertJsonObjectToCharSequence() {
        final JsonNode object = JsonNode.object()
            .set(
                JsonPropertyName.with("Hello"),
                JsonNode.string("World")
            );

        this.convertAndCheck(
            object,
            CharSequence.class,
            object.toString()
        );
    }

    @Override
    public JsonNodeConverterToJsonNodeText<FakeJsonNodeConverterContext> createConverter() {
        return JsonNodeConverterToJsonNodeText.instance();
    }

    @Override
    public FakeJsonNodeConverterContext createContext() {
        return new FakeJsonNodeConverterContext() {

            @Override
            public LineEnding lineEnding() {
                return LineEnding.NL;
            }

            @Override
            public boolean canConvert(final Object value,
                                      final Class<?> type) {
                return this.converter.canConvert(
                    value,
                    type,
                    this
                );
            }

            @Override
            public <T> Either<T, String> convert(final Object value,
                                                 final Class<T> target) {
                return this.converter.convert(
                    value,
                    target,
                    this
                );
            }

            private final Converter<FakeJsonNodeConverterContext> converter = Converters.collection(
                Lists.of(
                    JsonNodeConverters.toJsonNode(),
                    Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString()
                )
            );

            @Override
            public Optional<JsonString> typeName(final Class<?> type) {
                return this.marshallContext.typeName(type);
            }

            @Override
            public JsonNode marshall(final Object value) {
                return this.marshallContext.marshall(value);
            }

            private final JsonNodeMarshallContext marshallContext = JsonNodeMarshallContexts.basic();
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createConverter(),
            "* to JSON text"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeConverterToJsonNodeText<FakeJsonNodeConverterContext>> type() {
        return Cast.to(JsonNodeConverterToJsonNodeText.class);
    }
}
