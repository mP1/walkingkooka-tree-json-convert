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
import walkingkooka.convert.ConverterContext;
import walkingkooka.net.Url;
import walkingkooka.tree.json.JsonNode;

/**
 * A {@link Converter} that supports converting a {@link String} to one of the {@link JsonNode} sub-classes, using {@link JsonNode#parse(String)}.
 * If parsing fails an {@link IllegalArgumentException} will be thrown.
 */
final class StringToJsonNodeConverter<C extends ConverterContext> implements Converter<C> {

    /**
     * Type safe getter.
     */
    static <C extends ConverterContext> StringToJsonNodeConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static StringToJsonNodeConverter INSTANCE = new StringToJsonNodeConverter();

    private StringToJsonNodeConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return value instanceof String && JsonNode.isClass(type);
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
                this.successfulConversion(
                        JsonNode.parse(
                                (String) value
                        ).cast(
                                Cast.to(type)
                        ),
                        type
                ) :
                this.failConversion(
                        value,
                        type
                );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
