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
 * Used to convert from a wrapper to a primitive type
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class WrapperToPrimitive extends AbstractConverter {

	
	@Override
	protected boolean canHandleDestinationType(Type destinationType) {
		return destinationType.isPrimitive();
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return sourceObject != null;
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		if (super.canHandle(context, sourceObject, destinationType)) {
			PrimitiveType primitiveType = (PrimitiveType) destinationType;
			if (primitiveType.isBoolean() && sourceObject instanceof Boolean) {
				return true;
			} else if (primitiveType.isByte() && sourceObject instanceof Byte) {
				return true;
			} else if (primitiveType.isChar() && sourceObject instanceof Character) {
				return true;
			} else if (primitiveType.isDouble() && sourceObject instanceof Double) {
				return true;
			} else if (primitiveType.isFloat() && sourceObject instanceof Float) {
				return true;
			} else if (primitiveType.isInt() && sourceObject instanceof Integer) {
				return true;
			} else if (primitiveType.isLong() && sourceObject instanceof Long) {
				return true;
			} else if (primitiveType.isShort() && sourceObject instanceof Short) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		return sourceObject;
	}

}
