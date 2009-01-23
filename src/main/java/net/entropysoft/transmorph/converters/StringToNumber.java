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

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeUtils;

/**
 * Converter used when source object is a String and destination type is a
 * Number
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToNumber extends AbstractConverter {

	public Object doConvert(Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException("Cannot convert null to primitive number");
			} else
				return null;
		}
		try {
			String sourceString = (String) sourceObject;
			if (destinationType.getType().equals(Byte.TYPE)
					|| destinationType.getType().equals(Byte.class)) {
				return Byte.parseByte(sourceString);
			}
			if (destinationType.getType().equals(Double.TYPE)
					|| destinationType.getType().equals(Double.class)) {
				return Double.parseDouble(sourceString);
			}
			if (destinationType.getType().equals(Float.TYPE)
					|| destinationType.getType().equals(Float.class)) {
				return Float.parseFloat(sourceString);
			}
			if (destinationType.getType().equals(Integer.TYPE)
					|| destinationType.getType().equals(Integer.class)) {
				return Integer.parseInt(sourceString);
			}
			if (destinationType.getType().equals(Long.TYPE)
					|| destinationType.getType().equals(Long.class)) {
				return Long.parseLong(sourceString);
			}
			if (destinationType.getType().equals(Short.TYPE)
					|| destinationType.getType().equals(Short.class)) {
				return Short.parseShort(sourceString);
			}
			if (destinationType.getType().equals(BigInteger.class)) {
				return new BigInteger(sourceString);
			}
			if (destinationType.getType().equals(BigDecimal.class)) {
				return new BigDecimal(sourceString);
			}
			if (destinationType.getType().equals(Number.class)) {
				try {
					return Long.parseLong(sourceString);
				} catch (NumberFormatException e) {
					return Double.parseDouble(sourceString);
				}
			}
			throw new ConverterException("Could not convert");
		} catch (NumberFormatException e) {
			throw new ConverterException("Could not convert", e);
		} catch (ClassNotFoundException e) {
			throw new ConverterException("Could not convert", e);
		}
	}

	public boolean canHandleDestinationType(Type destinationType) {
		try {
			return TypeUtils.isNumberType(destinationType);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof String;
	}

}
