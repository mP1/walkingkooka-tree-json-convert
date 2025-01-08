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

/**
 * A {@link Converter} that supports marshalling a given {@link Object} to {@link JsonNode} using {@link JsonNodeConverterContext#marshall(Object)}.
 */
final class ToJsonNodeMarshallingConverter<C extends JsonNodeConverterContext> implements Converter<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> ToJsonNodeMarshallingConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ToJsonNodeMarshallingConverter INSTANCE = new ToJsonNodeMarshallingConverter();

    private ToJsonNodeMarshallingConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return null == value || context.isSupportedJsonType(value.getClass());
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
                context.marshall(
                    value
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
        return "* to " + JsonNode.class.getSimpleName();
    }
}
