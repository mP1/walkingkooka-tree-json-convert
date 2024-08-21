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
import walkingkooka.Cast;
import walkingkooka.ToStringTesting;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonPropertyName;
import walkingkooka.tree.json.JsonString;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public final class StringToClassConverterTest implements ConverterTesting2<StringToClassConverter<FakeJsonNodeConverterContext>, FakeJsonNodeConverterContext>,
        ToStringTesting<StringToClassConverter<FakeJsonNodeConverterContext>> {

    @Test
    public void testConvertStringToClassWithUnknownClassFails() {
        this.convertFails(
                "Unknown",
                Class.class
        );
    }

    @Test
    public void testConvertStringToClassWithSimpleClassFails() {
        this.convertFails(
                BigDecimal.class.getSimpleName(),
                Class.class
        );
    }

    @Test
    public void testConvertStringToClassBigDecimal() {
        this.convertStringAndCheck(
                BigDecimal.class.getName(),
                BigDecimal.class
        );
    }

    @Test
    public void testConvertStringToClassBigInteger() {
        this.convertStringAndCheck(
                BigInteger.class.getName(),
                BigInteger.class
        );
    }

    @Test
    public void testConvertStringToClassInteger() {
        this.convertStringAndCheck(
                Integer.class.getName(),
                Integer.class
        );
    }

    @Test
    public void testConvertStringToClassLong() {
        this.convertStringAndCheck(
                Long.class.getName(),
                Long.class
        );
    }

    private void convertStringAndCheck(final String className,
                                       final Class<?> expected) {
        this.convertAndCheck(
                className,
                Class.class,
                expected
        );
    }

    @Override
    public StringToClassConverter<FakeJsonNodeConverterContext> createConverter() {
        return StringToClassConverter.instance();
    }

    @Override
    public FakeJsonNodeConverterContext createContext() {
        return new FakeJsonNodeConverterContext() {
            @Override
            public Optional<Class<?>> registeredType(final JsonString string) {
                return JsonNodeMarshallContexts.basic()
                        .registeredType(string);
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
                StringToClassConverter.instance(),
                StringToClassConverter.class.getSimpleName()
        );
    }

    // class............................................................................................................

    @Override
    public Class<StringToClassConverter<FakeJsonNodeConverterContext>> type() {
        return Cast.to(StringToClassConverter.class);
    }
}
