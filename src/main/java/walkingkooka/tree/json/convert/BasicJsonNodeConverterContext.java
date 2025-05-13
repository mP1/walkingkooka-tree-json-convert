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

import walkingkooka.Either;
import walkingkooka.tree.expression.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContextDelegator;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContextDelegator;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A {@link JsonNodeConverterContext} that uses given {@link ExpressionNumberConverterContext}, {@link JsonNodeMarshallContext}
 * and {@link JsonNodeUnmarshallContext}. Note the {@link ExpressionNumberKind} returned for all context should be the same.
 */
final class BasicJsonNodeConverterContext implements JsonNodeConverterContext,
    JsonNodeMarshallContextDelegator,
    JsonNodeUnmarshallContextDelegator {

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

    // ConverterContext.................................................................................................


    @Override
    public long dateOffset() {
        return this.converterContext.dateOffset();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type) {
        return this.converterContext.canConvert(
            value,
            type
        );
    }

    @Override
    public <T> Either<T, String> convert(final Object value,
                                         final Class<T> type) {
        return this.converterContext.convert(
            value,
            type
        );
    }

    @Override
    public List<String> ampms() {
        return this.converterContext.ampms();
    }

    @Override
    public String ampm(final int hourOfDay) {
        return this.converterContext.ampm(hourOfDay);
    }

    @Override
    public List<String> monthNames() {
        return this.converterContext.monthNames();
    }

    @Override
    public String monthName(final int month) {
        return this.converterContext.monthName(month);
    }

    @Override
    public List<String> monthNameAbbreviations() {
        return this.converterContext.monthNameAbbreviations();
    }

    @Override
    public String monthNameAbbreviation(final int month) {
        return this.converterContext.monthNameAbbreviation(month);
    }

    @Override
    public List<String> weekDayNames() {
        return this.converterContext.weekDayNames();
    }

    @Override
    public String weekDayName(final int day) {
        return this.converterContext.weekDayName(day);
    }

    @Override
    public List<String> weekDayNameAbbreviations() {
        return this.converterContext.weekDayNameAbbreviations();
    }

    @Override
    public String weekDayNameAbbreviation(final int day) {
        return this.converterContext.weekDayNameAbbreviation(day);
    }

    @Override
    public int defaultYear() {
        return this.converterContext.defaultYear();
    }

    @Override
    public int twoDigitYear() {
        return this.converterContext.twoDigitYear();
    }

    @Override
    public int twoToFourDigitYear(final int year) {
        return this.converterContext.twoToFourDigitYear(year);
    }

    @Override
    public Locale locale() {
        return this.converterContext.locale();
    }

    @Override
    public LocalDateTime now() {
        return this.converterContext.now();
    }

    @Override
    public String currencySymbol() {
        return this.converterContext.currencySymbol();
    }

    @Override
    public char decimalSeparator() {
        return this.converterContext.decimalSeparator();
    }

    @Override
    public String exponentSymbol() {
        return this.converterContext.exponentSymbol();
    }

    @Override
    public char groupSeparator() {
        return this.converterContext.groupSeparator();
    }

    @Override
    public char percentSymbol() {
        return this.converterContext.percentSymbol();
    }

    @Override
    public char negativeSign() {
        return this.converterContext.negativeSign();
    }

    @Override
    public char positiveSign() {
        return this.converterContext.positiveSign();
    }

    @Override
    public MathContext mathContext() {
        return this.converterContext.mathContext();
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
