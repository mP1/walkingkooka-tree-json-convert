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

import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.convert.Converter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonString;

import java.util.Optional;

/**
 * A {@link Converter} that supports converting {@link String} to {@link Class} by querying the {@link walkingkooka.tree.json.marshall.JsonNodeContext}.
 */
final class StringToClassConverter<C extends JsonNodeConverterContext> implements Converter<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> StringToClassConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static StringToClassConverter INSTANCE = new StringToClassConverter();

    private StringToClassConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return value instanceof String && Class.class == type;
    }

    @Override
    public <T> Either<T, String> convert(final Object value,
                                         final Class<T> type,
                                         final C context) {
        return this.canConvert(
                value,
                type,
                context
        ) ?
                this.lookup(
                        (String)value,
                        type,
                        context
                ) :
                this.failConversion(
                        value,
                        type
                );
    }

    /**
     * Queries {@link walkingkooka.tree.json.marshall.JsonNodeContext#registeredType(JsonString)}.
     */
    private <T> Either<T, String> lookup(final String value,
                                         final Class<T> type,
                                         final C context) {
        return context.registeredType(
                JsonNode.string(value)
        ).map(
                k -> this.successfulConversion(
                        k,
                        type
                )
        ).orElseGet(
                () -> this.failConversion(
                        value,
                        type
                )
        );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}