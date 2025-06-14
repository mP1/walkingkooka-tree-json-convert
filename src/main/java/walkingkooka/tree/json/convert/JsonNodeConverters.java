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

import walkingkooka.convert.Converter;
import walkingkooka.reflect.PublicStaticHelper;

/**
 * A collection of {@link walkingkooka.convert.Converter} for walkingkooka.tree.json
 */
public final class JsonNodeConverters implements PublicStaticHelper {

    /**
     * {@see JsonNodeToUnmarshallingConverter}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> jsonNodeTo() {
        return JsonNodeToUnmarshallingConverter.instance();
    }

    /**
     * {@see TextToClassConverter}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> textToClass() {
        return TextToClassConverter.instance();
    }

    /**
     * {@see TextToJsonNodeConverter}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> textToJsonNode() {
        return TextToJsonNodeConverter.instance();
    }

    /**
     * {@see ToJsonNodeMarshallingConverter}
     */
    public static <C extends JsonNodeConverterContext> Converter<C> toJsonNode() {
        return ToJsonNodeMarshallingConverter.instance();
    }

    /**
     * Stop creation
     */
    private JsonNodeConverters() {
        throw new UnsupportedOperationException();
    }
}
