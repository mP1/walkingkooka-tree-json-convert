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
import walkingkooka.convert.Converter;
import walkingkooka.convert.TextToTryingShortCircuitingConverter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonString;

/**
 * A {@link Converter} that supports converting {@link String} to {@link Class} by querying the {@link walkingkooka.tree.json.marshall.JsonNodeContext}.
 */
final class TextToClassConverter<C extends JsonNodeConverterContext> implements TextToTryingShortCircuitingConverter<C> {

    /**
     * Type safe getter.
     */
    static <C extends JsonNodeConverterContext> TextToClassConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static TextToClassConverter<?> INSTANCE = new TextToClassConverter<>();

    private TextToClassConverter() {
        super();
    }

    @Override
    public boolean isTargetType(final Object value,
                                final Class<?> type,
                                final C context) {
        return Class.class == type;
    }

    /**
     * Queries {@link walkingkooka.tree.json.marshall.JsonNodeContext#registeredType(JsonString)}.
     */
    @Override
    public Object parseText(final String text,
                            final Class<?> type,
                            final C context) {
        return context.registeredType(
            JsonNode.string(text)
        ).orElseThrow(() -> new IllegalArgumentException("Unknown " + type.getName()));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
