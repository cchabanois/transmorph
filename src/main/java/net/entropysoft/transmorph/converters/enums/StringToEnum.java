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
package net.entropysoft.transmorph.converters.enums;

import java.text.MessageFormat;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Convert a string to the corresponding enumeration
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToEnum extends AbstractConverter {

	public StringToEnum() {
		this.useObjectPool = true;
	}
	
	public Object doConvert(ConversionContext context, Object sourceObject, Type destinationType) throws ConverterException {

		if (sourceObject == null) {
			return null;
		}
		String sourceString = (String) sourceObject;

		try {
			return Enum.valueOf(destinationType.getType(), sourceString);
		} catch (ClassNotFoundException e) {
			throw new ConverterException("Could not find class for "
					+ destinationType.getName());
		} catch (IllegalArgumentException e) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Enum type ''{0}'' has no constant with the specified name ''{1}''",
									destinationType.getName(), sourceString), e);
		}
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.getType().isEnum();
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof String;
	}

}
