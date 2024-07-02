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
import walkingkooka.convert.ConverterContext;
import walkingkooka.tree.expression.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContextObjectPostProcessor;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContextPreProcessor;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * A {@link JsonNodeConverterContext} that uses given {@link ExpressionNumberConverterContext}, {@link JsonNodeMarshallContext}
 * and {@link JsonNodeUnmarshallContext}. Note the {@link ExpressionNumberKind} returned for all context should be the same.
 */
final class BasicJsonNodeConverterContext implements JsonNodeConverterContext {

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
    public char percentageSymbol() {
        return this.converterContext.percentageSymbol();
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

    // JsonNodeMarshallContext..........................................................................................

    @Override
    public JsonNodeMarshallContext setObjectPostProcessor(final JsonNodeMarshallContextObjectPostProcessor processor) {
        final JsonNodeMarshallContext marshallContext = this.marshallContext.setObjectPostProcessor(processor);

        return this.marshallContext.equals(marshallContext) ?
                this :
                new BasicJsonNodeConverterContext(
                        this.converterContext,
                        marshallContext,
                        this.unmarshallContext
                );
    }

    @Override
    public JsonNode marshall(final Object value) {
        return this.marshallContext.marshall(value);
    }

    @Override
    public JsonNode marshallEnumSet(final Set<? extends Enum<?>> set) {
        return this.marshallContext.marshallEnumSet(set);
    }

    @Override
    public JsonNode marshallWithType(final Object value) {
        return this.marshallContext.marshallWithType(value);
    }

    @Override
    public JsonNode marshallCollection(final Collection<?> collection) {
        return this.marshallContext.marshallCollection(collection);
    }

    @Override
    public JsonNode marshallMap(final Map<?, ?> map) {
        return this.marshallContext.marshallMap(map);
    }

    @Override
    public JsonNode marshallWithTypeCollection(final Collection<?> collection) {
        return this.marshallContext.marshallWithTypeCollection(collection);
    }

    @Override
    public JsonNode marshallWithTypeMap(final Map<?, ?> map) {
        return this.marshallContext.marshallWithTypeMap(map);
    }

    private final JsonNodeMarshallContext marshallContext;

    // JsonNodeUnmarshallContext........................................................................................

    @Override
    public JsonNodeUnmarshallContext setPreProcessor(final JsonNodeUnmarshallContextPreProcessor processor) {
        final JsonNodeUnmarshallContext unmarshallContext = this.unmarshallContext.setPreProcessor(processor);

        return this.unmarshallContext.equals(unmarshallContext) ?
                this :
                new BasicJsonNodeConverterContext(
                        this.converterContext,
                        this.marshallContext,
                        unmarshallContext
                );
    }

    @Override
    public <T> T unmarshall(final JsonNode json, Class<T> type) {
        return this.unmarshallContext.unmarshall(
                json,
                type
        );
    }

    @Override
    public <T extends Enum<T>> Set<T> unmarshallEnumSet(final JsonNode jsonNode,
                                                        final Class<T> type,
                                                        final Function<String, T> mapper) {
        return this.unmarshallContext.unmarshallEnumSet(
                jsonNode,
                type,
                mapper
        );
    }

    @Override
    public <T> List<T> unmarshallList(final JsonNode json,
                                      final Class<T> type) {
        return this.unmarshallContext.unmarshallList(
                json,
                type
        );
    }

    @Override
    public <T> Set<T> unmarshallSet(final JsonNode json,
                                    final Class<T> type) {
        return this.unmarshallContext.unmarshallSet(
                json,
                type
        );
    }

    @Override
    public <K, V> Map<K, V> unmarshallMap(final JsonNode json,
                                          final Class<K> keyType,
                                          final Class<V> valueType) {
        return this.unmarshallContext.unmarshallMap(
                json,
                keyType,
                valueType
        );
    }

    @Override
    public <T> T unmarshallWithType(final JsonNode json) {
        return this.unmarshallContext.unmarshallWithType(json);
    }

    @Override
    public <T> List<T> unmarshallWithTypeList(final JsonNode json) {
        return this.unmarshallContext.unmarshallWithTypeList(json);
    }

    @Override
    public <T> Set<T> unmarshallWithTypeSet(final JsonNode json) {
        return this.unmarshallContext.unmarshallWithTypeSet(json);
    }

    @Override
    public <K, V> Map<K, V> unmarshallWithTypeMap(final JsonNode json) {
        return this.unmarshallContext.unmarshallWithTypeMap(json);
    }

    @Override
    public Optional<Class<?>> registeredType(final JsonString string) {
        return this.unmarshallContext.registeredType(string);
    }

    @Override
    public Optional<JsonString> typeName(final Class<?> type) {
        return this.unmarshallContext.typeName(type);
    }

    @Override
    public ExpressionNumberKind expressionNumberKind() {
        return this.unmarshallContext.expressionNumberKind();
    }

    private final JsonNodeUnmarshallContext unmarshallContext;

    // Object..........................................................................................................

    @Override
    public String toString() {
        return this.converterContext + " " + this.marshallContext + " " + this.unmarshallContext;
    }
}
