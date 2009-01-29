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

import java.text.MessageFormat;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IContainerConverter;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.IConverters;
import net.entropysoft.transmorph.type.Type;

/**
 * This converter tries each converter in order
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MultiConverter implements IConverter {

	private IConverter[] converters;

	public MultiConverter(IConverters converters) {
		this.converters = converters.getConverters();
		
		// use this converter as element converter for all container converters
		// that have no element converter set
		for (IConverter converter : this.converters) {
			if (converter instanceof IContainerConverter) {
				IContainerConverter containerConverter = (IContainerConverter) converter;
				if (containerConverter.getElementConverter() == null) {
					containerConverter.setElementConverter(this);
				}
			}
		}

	}

	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		for (IConverter converter : converters) {
			if (!canHandle(context, sourceObject, destinationType)) {
				return false;
			}
		}
		return true;
	}

	public Object convert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		ConverterException firstException = null;

		for (IConverter converter : converters) {
			if (converter.canHandle(context, sourceObject, destinationType)) {
				try {
					return converter.convert(context, sourceObject, destinationType);
				} catch (ConverterException e) {
					// canHandle do
					// not verify all cases. An other converter
					// might successfully convert the source to destination
					// type
					if (firstException == null)
						firstException = e;
				}
			}
		}

		if (firstException != null) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									sourceObject == null ? "null" : sourceObject.getClass()
											.getName(), destinationType),
					firstException);
		} else {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									sourceObject == null ? "null" : sourceObject.getClass()
											.getName(), destinationType));
		}

	}

}
