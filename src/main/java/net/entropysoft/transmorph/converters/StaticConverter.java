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

import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.beans.utils.ClassPair;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter that forces the use of a given converter for an object of a given
 * class to an object of a given destination class.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StaticConverter extends AbstractConverter {
	private final Map<ClassPair<?, ?>, IConverter> converters = new HashMap<ClassPair<?, ?>, IConverter>();

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return true;
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		if (!super.canHandle(context, sourceObject, destinationType)) {
			return false;
		}
		if (sourceObject == null) {
			return true;
		}
		IConverter converter = getConverter(sourceObject.getClass(),
				destinationType.getRawType());
		if (converter == null) {
			return false;
		} else {
			return converter.canHandle(context, sourceObject, destinationType);
		}

	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException(
						"Could not convert null to primitive type");
			} else {
				return null;
			}
		}
		IConverter converter = getConverter(sourceObject.getClass(),
				destinationType.getRawType());
		return converter.convert(context, sourceObject, destinationType);
	}

	private IConverter getConverter(Class<?> source, Class<?> dest) {
		ClassPair<?, ?> classPair = ClassPair.get(source, dest);
		return converters.get(classPair);
	}

	public void addConverter(Class<?> source, Class<?> dest,
			IConverter converter) {
		ClassPair<?, ?> classPair = ClassPair.get(source, dest);
		converters.put(classPair, converter);
	}

}
