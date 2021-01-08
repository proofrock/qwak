/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at usr/src/OPENSOLARIS.LICENSE
 * or http://www.opensolaris.org/os/licensing.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 * Copyright 2008 Aton S.p.A. (http://www.aton.eu).
 * Use is subject to license terms.
 */
package eu.germanorizzo.proj.qwak.internals.operations;

import eu.germanorizzo.proj.qwak.internals.EvalException;
import eu.germanorizzo.proj.qwak.internals.Operand;
import eu.germanorizzo.proj.qwak.internals.Operations;
import eu.germanorizzo.proj.qwak.internals.ValuedItem;

import java.math.BigDecimal;

public class GreaterThan implements Operations.Performable {
    @Override
    public Operand perform(Operand... operands) throws EvalException {
        if (operands.length != 2)
            throw new EvalException("Operands for GREATER must be 2, are " + operands.length);
        Operand o1 = operands[0];
        Operand o2 = operands[1];

        if (o1.getType() != o2.getType() && (o1.getType() == ValuedItem.Type.NULL || o2.getType() == ValuedItem.Type.NULL))
            throw new EvalException("Operands for GREATER must be of the same type or NULL");

        if (o1.getType() == ValuedItem.Type.NULL)
            return Operand.FALSE;
        if (o2.getType() == ValuedItem.Type.NULL)
            return Operand.TRUE;

        switch (o1.getType()) {
            case STRING: {
                int comparison = ((String) o1.getValue()).compareTo((String) o2.getValue());
                return Operand.boolItem(comparison >= 0);
            }
            case NUM: {
                int comparison = ((BigDecimal) o1.getValue()).compareTo((BigDecimal) o2.getValue());
                return Operand.boolItem(comparison >= 0);
            }
            case BOOL:
                return o1 != o2 ? o1 : Operand.FALSE;
        }

        throw new EvalException("Invalid parameters combination for GREATER");
    }
}