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

/**
 * Converter used when source object is a String and destination type is a
 * Number
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToNumber extends AbstractConverter {

	public StringToNumber() {
		// numbers are immutable so it is not necessary
		// it would make conversion slower and would take more memory when we
		// have to convert big arrays
		this.useObjectPool = false;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException(
						"Cannot convert null to primitive number");
			} else
				return null;
		}
		String sourceString = (String) sourceObject;
		try {
			if (destinationType.hasRawType(Byte.TYPE)
					|| destinationType.hasRawType(Byte.class)) {
				return Byte.parseByte(sourceString);
			}
			if (destinationType.hasRawType(Double.TYPE)
					|| destinationType.hasRawType(Double.class)) {
				return Double.parseDouble(sourceString);
			}
			if (destinationType.hasRawType(Float.TYPE)
					|| destinationType.hasRawType(Float.class)) {
				return Float.parseFloat(sourceString);
			}
			if (destinationType.hasRawType(Integer.TYPE)
					|| destinationType.hasRawType(Integer.class)) {
				return Integer.parseInt(sourceString);
			}
			if (destinationType.hasRawType(Long.TYPE)
					|| destinationType.hasRawType(Long.class)) {
				return Long.parseLong(sourceString);
			}
			if (destinationType.hasRawType(Short.TYPE)
					|| destinationType.hasRawType(Short.class)) {
				return Short.parseShort(sourceString);
			}
			if (destinationType.hasRawType(BigInteger.class)) {
				return new BigInteger(sourceString);
			}
			if (destinationType.hasRawType(BigDecimal.class)) {
				return new BigDecimal(sourceString);
			}
			if (destinationType.hasRawType(Number.class)) {
				try {
					return Long.parseLong(sourceString);
				} catch (NumberFormatException e) {
					return Double.parseDouble(sourceString);
				}
			}
			throw new ConverterException("Could not convert");
		} catch (NumberFormatException e) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert from ''{0}'' to object with type signature ''{1}''",
									sourceString, destinationType.toString()),
					e);
		}
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isNumber();
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof String;
	}

}
