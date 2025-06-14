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
import walkingkooka.ToStringTesting;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.convert.Converters;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.FakeExpressionNumberConverterContext;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeMarshallUnmarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;

public final class JsonNodeToUnmarshallingConverterTest implements ConverterTesting2<JsonNodeToUnmarshallingConverter<JsonNodeConverterContext>, JsonNodeConverterContext>,
    ToStringTesting<JsonNodeToUnmarshallingConverter<JsonNodeConverterContext>> {

    @Test
    public void testConvertJsonNodeTo() {
        final JsonNodeConverterContext context = this.createContext();
        final JsonNodeToUnmarshallingConverter converter = this.createConverter();

        final ExpressionNumber number = context.expressionNumberKind().create(123);

        this.convertAndCheck(
            converter,
            context.marshall(number),
            ExpressionNumber.class,
            context,
            number
        );
    }

    @Test
    public void testConvertTextToJsonNodeTo() {
        final JsonNodeConverterContext context = this.createContext();
        final JsonNodeToUnmarshallingConverter converter = this.createConverter();

        final ExpressionNumber number = context.expressionNumberKind().create(123);

        this.convertAndCheck(
            JsonNodeConverters.textToJsonNode().to(
                JsonNode.class,
                converter
            ),
            context.marshall(number).toString(),
            ExpressionNumber.class,
            context,
            number
        );
    }

    @Override
    public JsonNodeToUnmarshallingConverter createConverter() {
        return JsonNodeToUnmarshallingConverter.instance();
    }

    @Override
    public JsonNodeConverterContext createContext() {
        final ExpressionNumberKind kind = ExpressionNumberKind.BIG_DECIMAL;

        return JsonNodeConverterContexts.basic(
            new FakeExpressionNumberConverterContext() {

                @Override
                public ExpressionNumberKind expressionNumberKind() {
                    return kind;
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
                                                     final Class<T> type) {
                    return this.converter.convert(
                        value,
                        type,
                        this
                    );
                }

                private final Converter<FakeExpressionNumberConverterContext> converter = Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString();
            },
            JsonNodeMarshallUnmarshallContexts.basic(
                JsonNodeMarshallContexts.basic(),
                JsonNodeUnmarshallContexts.basic(
                    kind,
                    MathContext.DECIMAL32
                )
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createConverter(),
            "JsonNode to type"
        );
    }

    // class............................................................................................................

    @Override
    public Class<JsonNodeToUnmarshallingConverter<JsonNodeConverterContext>> type() {
        return Cast.to(JsonNodeToUnmarshallingConverter.class);
    }
}
