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

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeUtils;

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

	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
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
			if (destinationType.getType().equals(Number.class)
					|| destinationType.getType()
							.equals(sourceNumber.getClass())) {
				return sourceNumber;
			}
			if (destinationType.getType().equals(Byte.TYPE)
					|| destinationType.getType().equals(Byte.class)) {
				return sourceNumber.byteValue();
			}
			if (destinationType.getType().equals(Double.TYPE)
					|| destinationType.getType().equals(Double.class)) {
				return sourceNumber.doubleValue();
			}
			if (destinationType.getType().equals(Float.TYPE)
					|| destinationType.getType().equals(Float.class)) {
				return sourceNumber.floatValue();
			}
			if (destinationType.getType().equals(Integer.TYPE)
					|| destinationType.getType().equals(Integer.class)) {
				return sourceNumber.intValue();
			}
			if (destinationType.getType().equals(Long.TYPE)
					|| destinationType.getType().equals(Long.class)) {
				return sourceNumber.longValue();
			}
			if (destinationType.getType().equals(Short.TYPE)
					|| destinationType.getType().equals(Short.class)) {
				return sourceNumber.shortValue();
			}
			if (destinationType.getType().equals(BigInteger.class)) {
				return BigInteger.valueOf(sourceNumber.longValue());
			}
			if (destinationType.getType().equals(BigDecimal.class)) {
				return BigDecimal.valueOf(sourceNumber.doubleValue());
			}
			throw new ConverterException("Could not convert");
		} catch (NumberFormatException e) {
			throw new ConverterException("Could not convert", e);
		} catch (ClassNotFoundException e) {
			throw new ConverterException("Could not convert", e);
		}
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return TypeUtils.isNumberType(destinationType);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof Number;
	}

}
