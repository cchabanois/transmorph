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
import java.util.Iterator;
import java.util.LinkedList;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IContainerConverter;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * This converter tries each converter in order
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MultiConverter implements IContainerConverter {

	private LinkedList<IConverter> converterList;
	private IConverter elementConverter;
	
	public MultiConverter(IConverter... converters) {
		this.converterList = new LinkedList<IConverter>();

		for (IConverter converter : converters) {
			addConverter(converter);
		}

	}

	public IConverter[] getConverters() {
		return converterList.toArray(new IConverter[converterList.size()]);
	}
	
	public void addConverter(IConverter converter) {
		converterList.add(converter);
		if (converter instanceof IContainerConverter) {
			IContainerConverter containerConverter = (IContainerConverter) converter;
			if (containerConverter.getElementConverter() == null) {
				containerConverter.setElementConverter(elementConverter);
			}
		}
	}
	
	public void removeConverter(IConverter converter) {
		converterList.remove(converter);
	}
	
	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		return true;
	}

	public Object convert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		ConverterException firstException = null;

		int i = 0;
		for (Iterator<IConverter> it = converterList.iterator(); it.hasNext();) {
			IConverter converter = it.next();
			if (converter.canHandle(context, sourceObject, destinationType)) {
				try {
					Object result = converter.convert(context, sourceObject, destinationType);
					return result;
				} catch (ConverterException e) {
					// canHandle do
					// not verify all cases. An other converter
					// might successfully convert the source to destination
					// type
					if (firstException == null)
						firstException = e;
				}
			}
			i++;
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

	public IConverter getElementConverter() {
		return elementConverter;
	}

	public void setElementConverter(IConverter elementConverter) {
		this.elementConverter = elementConverter;
		
		for (IConverter converter : converterList) {
			if (converter instanceof IContainerConverter) {
				IContainerConverter containerConverter = (IContainerConverter) converter;
				if (containerConverter.getElementConverter() == null) {
					containerConverter.setElementConverter(this.elementConverter);
				}
			}
		}
	}

}
