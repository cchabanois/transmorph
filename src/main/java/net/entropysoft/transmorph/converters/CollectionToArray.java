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
import java.util.Collection;

import net.entropysoft.transmorph.ConverterContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.ArrayType;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source object type is collection and destination type is
 * an array
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class CollectionToArray extends AbstractContainerConverter {

	public CollectionToArray() {
		this.useObjectPool = true;
	}	
	
	public Object doConvert(ConverterContext context, Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Collection<Object> collection = (Collection<Object>) sourceObject;

		ArrayType arrayDestinationType = (ArrayType) destinationType;

		Type componentType = arrayDestinationType.getComponentType();

		Class componentTypeClass;
		try {
			componentTypeClass = componentType.getType();
		} catch (ClassNotFoundException e) {
			throw new ConverterException("Could not find class for "
					+ componentType.getName());
		}

		Object destinationArray = Array.newInstance(componentTypeClass,
				collection.size());
		
		if (useObjectPool) {
			context.getConvertedObjectPool().add(this, sourceObject, destinationType, destinationArray);
		}
		
		int i = 0;
		for (Object element : collection) {
			Object elementConverted = elementConverter.convert(
					context, element, componentType);
			Array.set(destinationArray, i, elementConverted);
			i++;
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
		return sourceObject instanceof Collection;
	}

}
