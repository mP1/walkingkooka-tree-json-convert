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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.provider.ConverterInfo;
import walkingkooka.convert.provider.ConverterName;
import walkingkooka.convert.provider.ConverterProvider;
import walkingkooka.net.UrlPath;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A {@link ConverterProvider} for {@link JsonNodeConverters}.
 */
final class JsonNodeConvertersConverterProvider implements ConverterProvider {

    final static JsonNodeConvertersConverterProvider INSTANCE = new JsonNodeConvertersConverterProvider();

    private JsonNodeConvertersConverterProvider() {
        super();
    }

    @Override
    public <C extends ConverterContext> Optional<Converter<C>> converter(final ConverterName name,
                                                                         final List<?> values) {
        Converter<?> converter;

        final List<?> copy = Lists.immutable(values);
        final int count = copy.size();

        switch (name.value()) {
            case JSON_NODE_TO_STRING:
                if (0 != count) {
                    throw new IllegalArgumentException("Expected 0 value(s) got " + count + " " + values);
                }

                converter = JsonNodeConverters.jsonNodeTo();
                break;
            case STRING_TO_JSON_NODE_STRING:
                if (0 != count) {
                    throw new IllegalArgumentException("Expected 0 value(s) got " + count + " " + values);
                }

                converter = JsonNodeConverters.stringToJsonNode();
                break;
            case TO_JSON_NODE_STRING:
                if (0 != count) {
                    throw new IllegalArgumentException("Expected 0 value(s) got " + count + " " + values);
                }

                converter = JsonNodeConverters.toJsonNode();
                break;
            default:
                converter = null;
                break;
        }

        return Optional.ofNullable(
                Cast.to(converter)
        );
    }

    final static String JSON_NODE_TO_STRING = "json-node-to";

    final static ConverterName JSON_NODE_TO = ConverterName.with(JSON_NODE_TO_STRING);

    final static String STRING_TO_JSON_NODE_STRING = "string-to-json-node";

    final static ConverterName STRING_TO_JSON_NODE = ConverterName.with(STRING_TO_JSON_NODE_STRING);

    final static String TO_JSON_NODE_STRING = "to-json-node";

    final static ConverterName TO_JSON_NODE = ConverterName.with(TO_JSON_NODE_STRING);

    @Override
    public Set<ConverterInfo> converterInfos() {
        return INFOS;
    }

    private final static Set<ConverterInfo> INFOS = Sets.of(
            nameToConverterInfo(JSON_NODE_TO),
            nameToConverterInfo(STRING_TO_JSON_NODE),
            nameToConverterInfo(TO_JSON_NODE)
    );

    private static ConverterInfo nameToConverterInfo(final ConverterName name) {
        return ConverterInfo.with(
                JsonNodeConverterProviders.BASE_URL.appendPath(
                        UrlPath.parse(
                                name.value()
                        )
                ),
                name
        );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
