/*
 * Copyright 2008-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.entropysoft.transmorph.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utility methods for {@link BigInteger} and {@link BigDecimal}
 * 
 * @author cedric
 * 
 */
public class BigNumberUtils {

	public static BigInteger getBigInteger(Number number) {
		BigInteger bigInteger = null;
		if (number instanceof Byte || number instanceof Short
				|| number instanceof Integer || number instanceof Long) {
			bigInteger = BigInteger.valueOf(number.longValue());
		} else if (number instanceof Float || number instanceof Double) {
			bigInteger = new BigDecimal(number.doubleValue()).toBigInteger();
		} else if (number instanceof BigInteger) {
			bigInteger = (BigInteger) number;
		} else if (number instanceof BigDecimal) {
			bigInteger = ((BigDecimal) number).toBigInteger();
		} else {
			// not a standard number
			bigInteger = new BigDecimal(number.doubleValue()).toBigInteger();
		}
		return bigInteger;
	}

	public static BigDecimal getBigDecimal(Number number) {
		BigDecimal bigDecimal = null;
		if (number instanceof Byte || number instanceof Short
				|| number instanceof Integer || number instanceof Long) {
			bigDecimal = new BigDecimal(number.longValue());
		} else if (number instanceof Float || number instanceof Double) {
			bigDecimal = BigDecimal.valueOf(number.doubleValue());
		} else if (number instanceof BigInteger) {
			bigDecimal = new BigDecimal((BigInteger) number);
		} else if (number instanceof BigDecimal) {
			bigDecimal = (BigDecimal) number;
		} else {
			// not a standard number
			bigDecimal = new BigDecimal(number.doubleValue());
		}
		return bigDecimal;
	}
}
