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
package net.entropysoft.transmorph.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;
import net.entropysoft.transmorph.utils.BigNumberUtils;
import net.entropysoft.transmorph.utils.NumberInRange;

/**
 * Converter used when source is a Number and destination is also a number
 * 
 * This may involve rounding or truncation.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class NumberToNumber extends AbstractConverter {
	private Number nullReplacementForPrimitive = null;
	private boolean checkOutOfRange = true;

	public NumberToNumber() {
		// number are immutable
		// it would make conversion slower and would take more memory when we
		// have to convert big arrays
		this.useObjectPool = false;
	}

	public Number getNullReplacementForPrimitive() {
		return nullReplacementForPrimitive;
	}

	public void setNullReplacementForPrimitive(Number nullReplacement) {
		this.nullReplacementForPrimitive = nullReplacement;
	}

	public void setCheckOutOfRange(boolean checkOutOfRange) {
		this.checkOutOfRange = checkOutOfRange;
	}

	public boolean isCheckOutOfRange() {
		return checkOutOfRange;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				if (nullReplacementForPrimitive != null) {
					sourceObject = nullReplacementForPrimitive;
				} else {
					throw new ConverterException(
							"Cannot convert null to a primitive number");
				}
			} else
				return null;
		}

		try {
			Number sourceNumber = (Number) sourceObject;
			if (destinationType.hasRawType(Number.class)
					|| destinationType.hasRawType(sourceNumber.getClass())) {
				return sourceNumber;
			}
			if (destinationType.hasRawType(Byte.TYPE)
					|| destinationType.hasRawType(Byte.class)) {
				checkInRangeIfNecessary(sourceNumber, NumberInRange.BYTE_MIN, NumberInRange.BYTE_MAX);
				return sourceNumber.byteValue();
			}
			if (destinationType.hasRawType(Double.TYPE)
					|| destinationType.hasRawType(Double.class)) {
				checkInRangeIfNecessary(sourceNumber, NumberInRange.DOUBLE_MIN, NumberInRange.DOUBLE_MAX);
				return sourceNumber.doubleValue();
			}
			if (destinationType.hasRawType(Float.TYPE)
					|| destinationType.hasRawType(Float.class)) {
				checkInRangeIfNecessary(sourceNumber, NumberInRange.FLOAT_MIN, NumberInRange.FLOAT_MAX);
				return sourceNumber.floatValue();
			}
			if (destinationType.hasRawType(Integer.TYPE)
					|| destinationType.hasRawType(Integer.class)) {
				checkInRangeIfNecessary(sourceNumber, NumberInRange.INTEGER_MIN, NumberInRange.INTEGER_MAX);
				return sourceNumber.intValue();
			}
			if (destinationType.hasRawType(Long.TYPE)
					|| destinationType.hasRawType(Long.class)) {
				checkInRangeIfNecessary(sourceNumber, NumberInRange.LONG_MIN, NumberInRange.LONG_MAX);
				return sourceNumber.longValue();
			}
			if (destinationType.hasRawType(Short.TYPE)
					|| destinationType.hasRawType(Short.class)) {
				checkInRangeIfNecessary(sourceNumber, NumberInRange.SHORT_MIN, NumberInRange.SHORT_MAX);
				return sourceNumber.shortValue();
			}
			if (destinationType.hasRawType(BigInteger.class)) {
				return BigNumberUtils.getBigInteger(sourceNumber);
			}
			if (destinationType.hasRawType(BigDecimal.class)) {
				return BigNumberUtils.getBigDecimal(sourceNumber);
			}
			throw new ConverterException("Could not convert");
		} catch (NumberFormatException e) {
			throw new ConverterException("Could not convert", e);
		}
	}
	
	private void checkInRangeIfNecessary(Number number, BigInteger min,
			BigInteger max) throws ConverterException {
		if (checkOutOfRange) {
			if (!NumberInRange.isInRange(number, min, max)) {
				throw new ConverterException(MessageFormat.format(
						"Could not convert {0} : out of range [{1},{2}]",
						number, min, max));
			}
		}
	}

	private void checkInRangeIfNecessary(Number number, BigDecimal min,
			BigDecimal max) throws ConverterException {
		if (checkOutOfRange) {
			if (!NumberInRange.isInRange(number, min, max)) {
				throw new ConverterException(MessageFormat.format(
						"Could not convert {0} : out of range [{1},{2}]",
						number, min, max));
			}
		}
	}	
	
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isNumber();
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof Number;
	}

}
