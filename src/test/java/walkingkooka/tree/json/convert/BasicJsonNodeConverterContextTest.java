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
import walkingkooka.ToStringTesting;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.datetime.DateTimeSymbols;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.ExpressionNumberConverterContexts;
import walkingkooka.tree.expression.ExpressionNumberConverters;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContexts;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContexts;

import java.math.MathContext;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicJsonNodeConverterContextTest implements JsonNodeConverterContextTesting<BasicJsonNodeConverterContext>,
    ToStringTesting<BasicJsonNodeConverterContext> {

    private final static ExpressionNumberConverterContext CONVERTER_CONTEXT = ExpressionNumberConverterContexts.basic(
        ExpressionNumberConverters.toNumberOrExpressionNumber(
            Converters.stringToNumber(
                (dnc) -> (DecimalFormat) DecimalFormat.getInstance()
            )
        ),
        ConverterContexts.basic(
            Converters.JAVA_EPOCH_OFFSET,
            Converters.fake(),
            DateTimeContexts.basic(
                DateTimeSymbols.fromDateFormatSymbols(
                    new DateFormatSymbols(
                        Locale.forLanguageTag("EN-AU")
                    )
                ),
                Locale.forLanguageTag("EN-AU"),
                1950,
                50,
                LocalDateTime::now
            ),
            DecimalNumberContexts.american(MathContext.DECIMAL32)
        ),
        ExpressionNumberKind.DEFAULT
    );

    private final static JsonNodeMarshallContext MARSHALL_CONTEXT = JsonNodeMarshallContexts.basic();

    private final static JsonNodeUnmarshallContext UNMARSHALL_CONTEXT = JsonNodeUnmarshallContexts.basic(
        ExpressionNumberKind.DEFAULT,
        CONVERTER_CONTEXT.mathContext()
    );

    @Test
    public void testWithNullConverterContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicJsonNodeConverterContext.with(
                null,
                MARSHALL_CONTEXT,
                UNMARSHALL_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullJsonNodeMarshallContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicJsonNodeConverterContext.with(
                CONVERTER_CONTEXT,
                null,
                UNMARSHALL_CONTEXT
            )
        );
    }

    @Test
    public void testWithNullJsonNodeUnmarshallContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> BasicJsonNodeConverterContext.with(
                CONVERTER_CONTEXT,
                MARSHALL_CONTEXT,
                null
            )
        );
    }

    @Override
    public String currencySymbol() {
        return CONVERTER_CONTEXT.currencySymbol();
    }

    @Override
    public char decimalSeparator() {
        return CONVERTER_CONTEXT.decimalSeparator();
    }

    @Override
    public String exponentSymbol() {
        return CONVERTER_CONTEXT.exponentSymbol();
    }

    @Override
    public char groupSeparator() {
        return CONVERTER_CONTEXT.groupSeparator();
    }

    @Override
    public MathContext mathContext() {
        return CONVERTER_CONTEXT.mathContext();
    }

    @Override
    public char negativeSign() {
        return CONVERTER_CONTEXT.negativeSign();
    }

    @Override
    public char percentSymbol() {
        return CONVERTER_CONTEXT.percentSymbol();
    }

    @Override
    public char positiveSign() {
        return CONVERTER_CONTEXT.positiveSign();
    }

    @Override
    public BasicJsonNodeConverterContext createContext() {
        return BasicJsonNodeConverterContext.with(
            CONVERTER_CONTEXT,
            MARSHALL_CONTEXT,
            UNMARSHALL_CONTEXT
        );
    }

    @Test
    public void testConvertStringToExpressionNumber() {
        final BasicJsonNodeConverterContext context = this.createContext();

        this.convertAndCheck(
            context,
            "123",
            ExpressionNumber.class,
            context.expressionNumberKind().create(123)
        );
    }

    @Test
    public void testMarshallThenUnmarshall() {
        final BasicJsonNodeConverterContext context = this.createContext();
        final ExpressionNumber number = context.expressionNumberKind().create(12);

        this.checkEquals(
            number,
            context.unmarshallWithType(
                context.marshallWithType(number)
            )
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createContext(),
            CONVERTER_CONTEXT + " " + MARSHALL_CONTEXT + " " + UNMARSHALL_CONTEXT
        );
    }

    // class............................................................................................................

    @Override
    public Class<BasicJsonNodeConverterContext> type() {
        return BasicJsonNodeConverterContext.class;
    }

    @Override
    public String typeNameSuffix() {
        return JsonNodeConverterContext.class.getSimpleName();
    }
}
