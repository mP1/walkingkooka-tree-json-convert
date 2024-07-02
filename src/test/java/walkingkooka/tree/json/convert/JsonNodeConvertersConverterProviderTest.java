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
import walkingkooka.convert.provider.ConverterProviderTesting;
import walkingkooka.reflect.JavaVisibility;

public final class JsonNodeConvertersConverterProviderTest implements ConverterProviderTesting<JsonNodeConvertersConverterProvider> {

    @Test
    public void testConverterJsonNodeTo() {
        this.converterAndCheck(
                JsonNodeConvertersConverterProvider.JSON_NODE_TO + "",
                JsonNodeConverters.jsonNodeTo()
        );
    }

    @Test
    public void testConverterStringToJsonNode() {
        this.converterAndCheck(
                JsonNodeConvertersConverterProvider.STRING_TO_JSON_NODE + "",
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
