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
 * Utility methods for {@link Number}s
 * 
 * @author cedric
 * 
 */
public class NumberUtils {

	public static boolean isByteShortIntegerOrLong(Number number) {
		return (number instanceof Byte || number instanceof Short
				|| number instanceof Integer || number instanceof Long);
	}

	public static boolean isFloatOrDouble(Number number) {
		return (number instanceof Float || number instanceof Double);
	}
	
	public static BigInteger getBigInteger(Number number) {
		BigInteger bigInteger = null;
		if (isByteShortIntegerOrLong(number)) {
			bigInteger = BigInteger.valueOf(number.longValue());
		} else if (isFloatOrDouble(number)) {
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
	
	@SuppressWarnings("unchecked")
	public static Class<? extends Number> getCommonNumberClass(Class<? extends Number> numberClass1, Class<? extends Number> numberClass2) {
		int indexInteger1 = getIndex(integerClasses, numberClass1);
		int indexInteger2;
		int indexDecimal1;
		int indexDecimal2;
		if (indexInteger1 >= 0) {
			indexInteger2 = getIndex(integerClasses, numberClass2);
			if (indexInteger2 >= 0) {
				if (indexInteger1 >= indexInteger2) {
					return integerClasses[indexInteger1];
				} else {
					return integerClasses[indexInteger2];
				}
			} else {
				return BigDecimal.class;
			}
		}
		indexDecimal1 = getIndex(decimalClasses, numberClass1);
		if (indexDecimal1 >= 0) {
			indexDecimal2 = getIndex(decimalClasses, numberClass2);
			if (indexDecimal2 >= 0) {
				if (indexDecimal1 >= indexDecimal2) {
					return decimalClasses[indexDecimal1];
				} else {
					return decimalClasses[indexDecimal2];
				}
			} else {
				return BigDecimal.class;
			}
		}
		return BigDecimal.class;
	}
	
	@SuppressWarnings("unchecked")
	private static Class[] integerClasses = new Class[] { Byte.class, Short.class, Integer.class, Long.class, BigInteger.class };
	@SuppressWarnings("unchecked")
	private static Class[] decimalClasses = new Class[] { Float.class, Double.class, BigDecimal.class };

	@SuppressWarnings("unchecked")
	private static int getIndex(Class[] classes, Class<? extends Number> numberClass) {
		for (int i = 0; i < classes.length; i++) {
			if (numberClass == classes[i]) {
				return i;
			}
		}
		return -1;
	}
	
	
}
