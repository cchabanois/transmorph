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

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter to convert from one type to another using multiple intermediate
 * types
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MultiStepConverter implements IConverter {

	private Type[] types;

	/**
	 * 
	 * @param types
	 *            first one is the source type. Last one is the destination
	 *            type. Others are intermediate types
	 */
	public MultiStepConverter(Type[] types) {
		this.types = types;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		return destinationType.equals(types[types.length - 1]);
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		try {
			return types[0].getType().equals(sourceObject.getClass());
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public Object convert(IConverter elementConverter, Object sourceObject,
			Type destinationType) throws ConverterException {
		Object stepSourceObject = sourceObject;
		for (int i = 1; i < types.length; i++) {
			stepSourceObject = elementConverter.convert(elementConverter,
					stepSourceObject, types[i]);
		}
		return stepSourceObject;
	}

}
