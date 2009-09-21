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
package net.entropysoft.transmorph.converters.collections;

import java.lang.reflect.Array;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.TypeReference;

public class ArrayToString extends AbstractContainerConverter {

	private IStringArrayFormatter stringArrayFormatter = new DefaultStringArrayFormatter();
	
	public ArrayToString() {
		this.useObjectPool = false;
	}

	public IStringArrayFormatter getStringArrayFormatter() {
		return stringArrayFormatter;
	}

	public void setStringArrayFormatter(IStringArrayFormatter stringArrayFormatter) {
		this.stringArrayFormatter = stringArrayFormatter;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Object array = sourceObject;
		int arrayLength = Array.getLength(array);
		
		String[] stringArray = new String[arrayLength];

		for (int i = 0; i < arrayLength; i++) {
			Object element = Array.get(array, i);
			String elementConverted = (String)elementConverter.convert(context, element,
					destinationType);
			stringArray[i] = elementConverted;
		}
		
		return stringArrayFormatter.format(stringArray);
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isType(String.class);
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
