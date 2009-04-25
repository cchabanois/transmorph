/*
 * Copyright 2008-2009 the original author or authors.
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
 * Check if number are in a given range 
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class NumberInRange {

	public static final BigInteger BYTE_MIN = BigInteger
			.valueOf((long) Byte.MIN_VALUE);
	public static final BigInteger BYTE_MAX = BigInteger
			.valueOf((long) Byte.MAX_VALUE);
	public static final BigInteger SHORT_MIN = BigInteger
			.valueOf((long) Short.MIN_VALUE);
	public static final BigInteger SHORT_MAX = BigInteger
			.valueOf((long) Short.MAX_VALUE);
	public static final BigInteger INTEGER_MIN = BigInteger
			.valueOf((long) Integer.MIN_VALUE);
	public static final BigInteger INTEGER_MAX = BigInteger
			.valueOf((long) Integer.MAX_VALUE);
	public static final BigInteger LONG_MIN = BigInteger
			.valueOf(Long.MIN_VALUE);
	public static final BigInteger LONG_MAX = BigInteger
			.valueOf(Long.MAX_VALUE);

	public static final BigDecimal FLOAT_MAX = new BigDecimal(Float.MAX_VALUE);
	public static final BigDecimal FLOAT_MIN = new BigDecimal(-Float.MAX_VALUE);
	public static final BigDecimal DOUBLE_MAX = new BigDecimal(Double.MAX_VALUE);
	public static final BigDecimal DOUBLE_MIN = new BigDecimal(
			-Double.MAX_VALUE);

	public static boolean isInByteRange(Number number) {
		return isInRange(number, BYTE_MIN, BYTE_MAX);
	}

	public static boolean isInShortRange(Number number) {
		return isInRange(number, SHORT_MIN, SHORT_MAX);
	}

	public static boolean isInIntegerRange(Number number) {
		return isInRange(number, INTEGER_MIN, INTEGER_MAX);
	}

	public static boolean isInLongRange(Number number) {
		return isInRange(number, LONG_MIN, LONG_MAX);
	}

	public static boolean isInRange(Number number, BigInteger min,
			BigInteger max) {
		try {
			BigInteger bigInteger = null;
			if (number instanceof Byte || number instanceof Short
					|| number instanceof Integer || number instanceof Long) {
				bigInteger = BigInteger.valueOf(number.longValue());
			} else if (number instanceof Float || number instanceof Double) {
				bigInteger = new BigDecimal(number.doubleValue())
						.toBigInteger();
			} else if (number instanceof BigInteger) {
				bigInteger = (BigInteger) number;
			} else if (number instanceof BigDecimal) {
				bigInteger = ((BigDecimal) number).toBigInteger();
			} else {
				// not a standard number
				bigInteger = new BigDecimal(number.doubleValue())
						.toBigInteger();
			}
			return max.compareTo(bigInteger) >= 0
					&& min.compareTo(bigInteger) <= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isInRange(Number number, BigDecimal min,
			BigDecimal max) {
		try {
			BigDecimal bigDecimal = null;
			if (number instanceof Byte || number instanceof Short
					|| number instanceof Integer || number instanceof Long) {
				bigDecimal = new BigDecimal(number.longValue());
			} else if (number instanceof Float || number instanceof Double) {
				bigDecimal = new BigDecimal(number.doubleValue());
			} else if (number instanceof BigInteger) {
				bigDecimal = new BigDecimal((BigInteger) number);
			} else if (number instanceof BigDecimal) {
				bigDecimal = (BigDecimal) number;
			} else {
				bigDecimal = new BigDecimal(number.doubleValue());
			}
			return max.compareTo(bigDecimal) >= 0
					&& min.compareTo(bigDecimal) <= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isInFloatRange(Number number) {
		return isInRange(number, FLOAT_MIN, FLOAT_MAX);
	}

	public static boolean isInDoubleRange(Number number) {
		return isInRange(number, DOUBLE_MIN, DOUBLE_MAX);
	}

}
