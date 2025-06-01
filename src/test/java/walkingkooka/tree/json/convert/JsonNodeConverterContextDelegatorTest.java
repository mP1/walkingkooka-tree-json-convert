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

import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContextDelegator;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContexts;
import walkingkooka.tree.json.convert.JsonNodeConverterContextDelegatorTest.TestJsonNodeConverterContextDelegator;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;

public final class JsonNodeConverterContextDelegatorTest implements JsonNodeConverterContextTesting<TestJsonNodeConverterContextDelegator>,
    DecimalNumberContextDelegator {

    @Override
    public void testCheckToStringOverridden() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestJsonNodeConverterContextDelegator createContext() {
        return new TestJsonNodeConverterContextDelegator();
    }

    @Override
    public DecimalNumberContext decimalNumberContext() {
        return DECIMAL_NUMBER_CONTEXT;
    }

    private final static MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    private final static DecimalNumberContext DECIMAL_NUMBER_CONTEXT = DecimalNumberContexts.american(MATH_CONTEXT);

    @Override
    public MathContext mathContext() {
        return MATH_CONTEXT;
    }

    // class............................................................................................................

    @Override
    public Class<TestJsonNodeConverterContextDelegator> type() {
        return TestJsonNodeConverterContextDelegator.class;
    }

    static class TestJsonNodeConverterContextDelegator implements JsonNodeConverterContextDelegator {

        @Override
        public JsonNodeConverterContext jsonNodeConverterContext() {
            final ExpressionNumberKind numberKind = ExpressionNumberKind.BIG_DECIMAL;
            final Locale locale = Locale.ENGLISH;

            return JsonNodeConverterContexts.basic(
                ExpressionNumberConverterContexts.basic(
                    Converters.fake(),
                    ConverterContexts.basic(
                        0, // dateOffset
                        Converters.fake(),
                        DateTimeContexts.basic(
                            DateTimeSymbols.fromDateFormatSymbols(
                                new DateFormatSymbols(locale)
                            ),
                            locale,
                            1950, // defaultYear
                            50, // twoDigitYear
                            LocalDateTime::now
                        ),
                        DECIMAL_NUMBER_CONTEXT
                    ),
                    numberKind
                ),
                JsonNodeMarshallContexts.basic(),
                JsonNodeUnmarshallContexts.basic(
                    numberKind,
                    MATH_CONTEXT
                )
            );
        }
    }
}
