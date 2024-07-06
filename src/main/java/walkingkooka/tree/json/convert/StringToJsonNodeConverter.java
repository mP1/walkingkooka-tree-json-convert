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

import javax.swing.text.html.HTMLDocument.RunElement;

/**
 * A {@link Converter} that supports converting a {@link String} to one of the {@link JsonNode} sub-classes.
 */
final class StringToJsonNodeConverter<C extends JsonNodeConverterContext> implements Converter<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> StringToJsonNodeConverter<C> instance() {
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
                this.parseJson(
                        (String)value,
                        type
                ) :
                this.failConversion(
                        value,
                        type
                );
    }

    /**
     * Tries to parse the {@link String} as JSON, catching any parsing exceptions and turning them into failures.
     */
    private <T> Either<T, String> parseJson(final String value,
                                            final Class<T> type) {
        Either<T, String> result;

        try {
            result = this.successfulConversion(
                    JsonNode.parse(value)
                            .cast(
                                    Cast.to(type)
                            ),
                    type
            );
        } catch (final RuntimeException cause) {
            result = this.failConversion(
                    value,
                    type
            );
        }

        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
