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
 * Converter used when source is a char array and destination is a String
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToCharacterArray extends AbstractConverter {

	public StringToCharacterArray() {
		this.useObjectPool = false;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		String str = (String) sourceObject;
		return str.toCharArray();
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		if (!destinationType.isArray()) {
			return false;
		}
		if (!destinationType.getArrayComponentType().isType(Character.TYPE)) {
			return false;
		}
		return true;
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof String;
	}

}
