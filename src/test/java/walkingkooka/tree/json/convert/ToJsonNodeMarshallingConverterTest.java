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
import walkingkooka.convert.Converters;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.FakeExpressionNumberConverterContext;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;

public final class ToJsonNodeMarshallingConverterTest implements ConverterTesting2<ToJsonNodeMarshallingConverter<JsonNodeConverterContext>, JsonNodeConverterContext>,
    ToStringTesting<ToJsonNodeMarshallingConverter<JsonNodeConverterContext>> {

    @Test
    public void testConvertToJsonNode() {
        final JsonNodeConverterContext context = this.createContext();
        final ToJsonNodeMarshallingConverter converter = this.createConverter();

        final ExpressionNumber number = context.expressionNumberKind().create(123);

        this.convertAndCheck(
            converter,
            number,
            JsonNode.class,
            context,
            context.marshall(number)
        );
    }

    @Test
    public void testConvertStringToJsonNodeTo() {
        final JsonNodeConverterContext context = this.createContext();

        final ExpressionNumber number = context.expressionNumberKind()
            .create(123);

        this.convertAndCheck(
            JsonNodeConverters.toJsonNode()
                .to(
                    String.class,
                    Converters.objectToString()
                ),
            number,
            String.class,
            context,
            context.marshall(number).toString()
        );
    }

    @Override
    public ToJsonNodeMarshallingConverter createConverter() {
        return ToJsonNodeMarshallingConverter.instance();
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
            },
            JsonNodeMarshallContexts.basic(),
            JsonNodeUnmarshallContexts.basic(
                kind,
                MathContext.DECIMAL32
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createConverter(),
            "* to JsonNode"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ToJsonNodeMarshallingConverter<JsonNodeConverterContext>> type() {
        return Cast.to(ToJsonNodeMarshallingConverter.class);
    }
}
