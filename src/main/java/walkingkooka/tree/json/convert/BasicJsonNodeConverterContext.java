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

import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContextDelegator;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContext;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContextDelegator;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContextDelegator;

import java.math.MathContext;
import java.util.Objects;

/**
 * A {@link JsonNodeConverterContext} that uses given {@link ExpressionNumberConverterContext}, {@link JsonNodeMarshallContext}
 * and {@link JsonNodeUnmarshallContext}. Note the {@link ExpressionNumberKind} returned for all context should be the same.
 */
final class BasicJsonNodeConverterContext implements JsonNodeConverterContext,
    JsonNodeMarshallContextDelegator,
    JsonNodeUnmarshallContextDelegator,
    ConverterContextDelegator {

    static BasicJsonNodeConverterContext with(final ExpressionNumberConverterContext converterContext,
                                              final JsonNodeMarshallContext marshallContext,
                                              final JsonNodeUnmarshallContext unmarshallContext) {
        return new BasicJsonNodeConverterContext(
            Objects.requireNonNull(converterContext, "converterContext"),
            Objects.requireNonNull(marshallContext, "marshallContext"),
            Objects.requireNonNull(unmarshallContext, "unmarshallContext")
        );
    }

    private BasicJsonNodeConverterContext(final ExpressionNumberConverterContext converterContext,
                                          final JsonNodeMarshallContext marshallContext,
                                          final JsonNodeUnmarshallContext unmarshallContext) {
        this.converterContext = converterContext;
        this.marshallContext = marshallContext;
        this.unmarshallContext = unmarshallContext;
    }

    // ConverterContextDelegator........................................................................................

    @Override
    public ConverterContext converterContext() {
        return this.converterContext;
    }

    // DecimalNumberContextDelegator....................................................................................

    @Override
    public ExpressionNumberKind expressionNumberKind() {
        // prefer to source ExpressionNumberKind from ExpressionNumberConverterContext rather than JsonNodeUnmarshallContext
        return this.converterContext.expressionNumberKind();
    }

    @Override
    public MathContext mathContext() {
        return this.converterContext.mathContext();
    }

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return this.converterContext;
    }

    private final ExpressionNumberConverterContext converterContext;

    // JsonNodeContext..................................................................................................

    @Override
    public JsonNodeContext jsonNodeContext() {
        return this.marshallContext;
    }

    // JsonNodeMarshallContext..........................................................................................

    @Override
    public JsonNodeMarshallContext jsonNodeMarshallContext() {
        return this.marshallContext;
    }

    private final JsonNodeMarshallContext marshallContext;

    // JsonNodeUnmarshallContext........................................................................................

    @Override
    public JsonNodeUnmarshallContext jsonNodeUnmarshallContext() {
        return this.unmarshallContext;
    }

    private final JsonNodeUnmarshallContext unmarshallContext;

    // Object..........................................................................................................

    @Override
    public String toString() {
        return this.converterContext + " " + this.marshallContext + " " + this.unmarshallContext;
    }
}
