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

import java.text.MessageFormat;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.PrimitiveType;
import net.entropysoft.transmorph.type.Type;

/**
 * converter used when source object type and destination type are compatible.
 * No conversion is done
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class IdentityConverter extends AbstractConverter {

	public Object doConvert(ConversionContext context, Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			// always handle the case where source object is null if
			// destination type is not a primitive
			if (destinationType.isPrimitive()) {
				throw new ConverterException(
						"Cannot convert null to a primitive type");
			}
		}

		if (destinationType.isPrimitive()) {
			// also handle the case where destination type is a primitive type
			// and source object is a wrapper
			PrimitiveType primitiveType = (PrimitiveType)destinationType;
			if (primitiveType.isBoolean()
					&& sourceObject instanceof Boolean) {
				return sourceObject;
			} else
			if (primitiveType.isByte()
					&& sourceObject instanceof Byte) {
				return sourceObject;
			} else
			if (primitiveType.isChar()
					&& sourceObject instanceof Character) {
				return sourceObject;
			} else
			if (primitiveType.isDouble()
					&& sourceObject instanceof Double) {
				return sourceObject;
			} else
			if (primitiveType.isFloat()
					&& sourceObject instanceof Float) {
				return sourceObject;
			} else
			if (primitiveType.isInt()
					&& sourceObject instanceof Integer) {
				return sourceObject;
			} else
			if (primitiveType.isLong()
					&& sourceObject instanceof Long) {
				return sourceObject;
			} else
			if (primitiveType.isShort()
					&& sourceObject instanceof Short) {
				return sourceObject;
			} else {
				throw new ConverterException(MessageFormat.format(
						"Cannot convert ''{0}'' to primitive type", sourceObject.getClass()));
			}
		}

		try {
			if (destinationType.isSuperOf(sourceObject.getClass())) {
				return sourceObject;
			}
		} catch (ClassNotFoundException e) {
			throw new ConverterException(MessageFormat.format("Cannot convert ''{0}'' to ''{1}''",
					sourceObject.getClass(), destinationType.getName()));
		}
		throw new ConverterException(MessageFormat.format("Cannot convert ''{0}'' to ''{1}''",
				sourceObject.getClass(), destinationType.getName()));
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		return true;
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

}
