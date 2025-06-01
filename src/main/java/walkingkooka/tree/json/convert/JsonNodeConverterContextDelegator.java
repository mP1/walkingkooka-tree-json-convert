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

import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContextDelegator;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContextDelegator;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContextDelegator;

import java.math.MathContext;

public interface JsonNodeConverterContextDelegator extends JsonNodeConverterContext,
    ExpressionNumberConverterContextDelegator,
    JsonNodeMarshallContextDelegator,
    JsonNodeUnmarshallContextDelegator {

    JsonNodeConverterContext jsonNodeConverterContext();

    // ExpressionNumberConverterContextDelegator........................................................................

    @Override
    default ExpressionNumberConverterContext expressionNumberConverterContext() {
        return this.jsonNodeConverterContext();
    }

    @Override
    default ExpressionNumberKind expressionNumberKind() {
        return this.expressionNumberConverterContext()
            .expressionNumberKind();
    }

    @Override
    default MathContext mathContext() {
        return this.expressionNumberConverterContext()
            .mathContext();
    }

    // JsonNodeMarshallContextDelegator.................................................................................

    @Override
    default JsonNodeMarshallContext jsonNodeMarshallContext() {
        return this.jsonNodeConverterContext();
    }

    // JsonNodeUnmarshallContextDelegator...............................................................................

    @Override
    default JsonNodeUnmarshallContext jsonNodeUnmarshallContext() {
        return this.jsonNodeConverterContext();
    }

    @Override
    default JsonNodeContext jsonNodeContext() {
        return this.jsonNodeConverterContext();
    }
}
