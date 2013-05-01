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
 * Converter that converts a source object to a destination type using one of
 * its given subtypes.
 * 
 * This can be used to convert a source object to Object using {@link String} or
 * {@link Number} as possible destination type.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class SubTypeConverter extends AbstractContainerConverter {
	private final TypeReference<?> destinationType;
	private final TypeReference<?>[] destinationSubTypes;
	private final Map<TypeReference<?>, IConverter> typeReferenceToConverter = new HashMap<TypeReference<?>, IConverter>();

	public SubTypeConverter(TypeReference<?> destinationType,
			TypeReference<?>[] destinationSubTypes) {
		this.destinationType = destinationType;
		this.destinationSubTypes = destinationSubTypes;
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
		return this.destinationType
				.isRawTypeSubOf(destinationType.getRawType());
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		ConverterException firstException = null;

		for (TypeReference<?> destinationSubType : destinationSubTypes) {
			try {
				IConverter converter = typeReferenceToConverter
						.get(destinationSubType);
				if (converter == null) {
					converter = elementConverter;
				}

				if (converter.canHandle(context, sourceObject,
						destinationSubType)) {
					return converter.convert(context, sourceObject,
							destinationSubType);
				}
			} catch (ConverterException e) {
				if (firstException == null)
					firstException = e;
			}
		}

		if (firstException != null) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									sourceObject == null ? "null"
											: sourceObject.getClass().getName(),
									destinationType), firstException);
		} else {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									sourceObject == null ? "null"
											: sourceObject.getClass().getName(),
									destinationType));
		}
	}

}
