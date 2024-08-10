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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.provider.ConverterProviderTesting;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;

public final class JsonNodeConvertersConverterProviderTest implements ConverterProviderTesting<JsonNodeConvertersConverterProvider> {

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testConverterSelectorJsonNodeTo() {
        this.converterAndCheck(
                JsonNodeConvertersConverterProvider.JSON_NODE_TO + "",
                CONTEXT,
                JsonNodeConverters.jsonNodeTo()
        );
    }

    @Test
    public void testConverterSelectorStringToJsonNode() {
        this.converterAndCheck(
                JsonNodeConvertersConverterProvider.STRING_TO_JSON_NODE + "",
                CONTEXT,
                JsonNodeConverters.stringToJsonNode()
        );
    }

    @Test
    public void testConverterNameJsonNodeTo() {
        this.converterAndCheck(
                JsonNodeConvertersConverterProvider.JSON_NODE_TO,
                Lists.empty(),
                CONTEXT,
                JsonNodeConverters.jsonNodeTo()
        );
    }

    @Test
    public void testConverterNameStringToJsonNode() {
        this.converterAndCheck(
                JsonNodeConvertersConverterProvider.STRING_TO_JSON_NODE,
                Lists.empty(),
                CONTEXT,
                JsonNodeConverters.stringToJsonNode()
        );
    }

    @Override
    public JsonNodeConvertersConverterProvider createConverterProvider() {
        return JsonNodeConvertersConverterProvider.INSTANCE;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public Class<JsonNodeConvertersConverterProvider> type() {
        return JsonNodeConvertersConverterProvider.class;
    }
}
