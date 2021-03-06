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

import java.lang.reflect.Array;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter used to convert a single element to an array (with one element)
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class SingleElementToArray extends AbstractContainerConverter {

	public Object doConvert(ConversionContext context, Object sourceObject, TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		TypeReference<?> elementType = destinationType.getArrayElementType();
		Class<?> destinationComponentType = elementType.getRawType();

		Object destinationArray = Array
				.newInstance(destinationComponentType, 1);

		Object elementConverted = elementConverter.convert(context,
				sourceObject, elementType);
		Array.set(destinationArray, 0, elementConverted);

		return destinationArray;
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		if (!destinationType.isArray()) {
			return false;
		}
		// we don't handle arrays with more than one dimension
		if (destinationType.getArrayNumDimensions() > 1) {
			return false;
		}
		return true;
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

}
