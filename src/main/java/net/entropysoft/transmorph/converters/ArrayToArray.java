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
import net.entropysoft.transmorph.type.ArrayType;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source object type is array and destination type is also
 * an array (with different element type)
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ArrayToArray extends AbstractContainerConverter {

	public ArrayToArray() {
		this.useObjectPool = true;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Object array = sourceObject;
		ArrayType arrayDestinationType = (ArrayType) destinationType;

		Type componentType = arrayDestinationType.getComponentType();
		int arrayLength = Array.getLength(array);

		Class componentTypeClass;
		try {
			componentTypeClass = componentType.getType();
		} catch (ClassNotFoundException e) {
			throw new ConverterException("Could not find class for "
					+ componentType.getName());
		}

		Object destinationArray = Array.newInstance(componentTypeClass,
				arrayLength);

		if (useObjectPool) {
			// add now to the pool so that array elements could reference it
			context.getConvertedObjectPool().add(this, sourceObject,
					arrayDestinationType, destinationArray);
		}

		for (int i = 0; i < arrayLength; i++) {
			Object element = Array.get(array, i);
			Object elementConverted = elementConverter.convert(context, element,
					componentType);
			Array.set(destinationArray, i, elementConverted);
		}

		return destinationArray;
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		return destinationType.isArray();
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		if (!sourceObject.getClass().isArray()) {
			return false;
		}
		return true;
	}

}
