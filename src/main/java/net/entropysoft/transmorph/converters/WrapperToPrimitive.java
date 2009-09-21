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

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Used to convert from a wrapper to a primitive type
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class WrapperToPrimitive extends AbstractConverter {

	
	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isPrimitive();
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return sourceObject != null;
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		if (super.canHandle(context, sourceObject, destinationType)) {
			if (destinationType.isType(Boolean.TYPE) && sourceObject instanceof Boolean) {
				return true;
			} else if (destinationType.isType(Byte.TYPE) && sourceObject instanceof Byte) {
				return true;
			} else if (destinationType.isType(Character.TYPE) && sourceObject instanceof Character) {
				return true;
			} else if (destinationType.isType(Double.TYPE) && sourceObject instanceof Double) {
				return true;
			} else if (destinationType.isType(Float.TYPE) && sourceObject instanceof Float) {
				return true;
			} else if (destinationType.isType(Integer.TYPE) && sourceObject instanceof Integer) {
				return true;
			} else if (destinationType.isType(Long.TYPE) && sourceObject instanceof Long) {
				return true;
			} else if (destinationType.isType(Short.TYPE) && sourceObject instanceof Short) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		return sourceObject;
	}

}
