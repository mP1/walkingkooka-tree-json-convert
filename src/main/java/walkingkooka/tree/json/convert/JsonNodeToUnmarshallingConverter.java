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
 * A {@link Converter} that supports unmarshalling a given {@link JsonNode} to the target type using {@link JsonNodeConverterContext#unmarshallWithType(JsonNode)}.
 */
final class JsonNodeToUnmarshallingConverter<C extends JsonNodeConverterContext> implements Converter<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> JsonNodeToUnmarshallingConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static JsonNodeToUnmarshallingConverter INSTANCE = new JsonNodeToUnmarshallingConverter();

    private JsonNodeToUnmarshallingConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return value instanceof JsonNode && context.isSupportedJsonType(type);
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
                context.unmarshall(
                    (JsonNode) value,
                    type
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
        return JsonNode.class.getSimpleName() + " to type";
    }
}
