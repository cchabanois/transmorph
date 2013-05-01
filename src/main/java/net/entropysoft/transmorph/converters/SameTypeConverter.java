/*
 * Copyright 2008-2010 the original author or authors.
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
import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter that converts a source object to the same destination type as the
 * source object.
 * 
 * This can be used to clone a source object.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class SameTypeConverter extends AbstractContainerConverter {
	private final Map<TypeReference<?>, IConverter> typeReferenceToConverter = new HashMap<TypeReference<?>, IConverter>();

	public SameTypeConverter() {
	}

	/**
	 * Set the converter to use to convert to given destination subtype (as
	 * given in constructor)
	 * 
	 * @param destinationSubType
	 * @param converter
	 */
	public void setConverter(TypeReference<?> destinationSubType,
			IConverter converter) {
		typeReferenceToConverter.put(destinationSubType, converter);
	}

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
		return sourceObject == null || destinationType.isRawTypeInstance(sourceObject);
	}
	
	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {

		if (sourceObject == null) {
			return null;
		}
		
		TypeReference<?> sourceObjectTypeReference = TypeReference
				.get(sourceObject.getClass());
		try {
			IConverter converter = typeReferenceToConverter
					.get(sourceObjectTypeReference);
			if (converter == null) {
				converter = elementConverter;
			}

			if (converter.canHandle(context, sourceObject,
					sourceObjectTypeReference)) {
				return converter.convert(context, sourceObject,
						sourceObjectTypeReference);
			}
		} catch (ConverterException e) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									sourceObject == null ? "null"
											: sourceObject.getClass().getName(),
									destinationType), e);
		}
		throw new ConverterException(
				MessageFormat
						.format(
								"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
								sourceObject == null ? "null" : sourceObject
										.getClass().getName(), destinationType));
	}

}