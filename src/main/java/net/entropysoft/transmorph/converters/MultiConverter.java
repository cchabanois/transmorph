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
public class MultiConverter extends AbstractContainerConverter {

	private LinkedList<IConverter> converterList;
	private boolean canReorder;

	public MultiConverter(IConverter... converters) {
		this(false, converters);
	}

	public MultiConverter(boolean canReorder, IConverter... converters) {
		this.useObjectPool = false;
		this.canReorder = canReorder;
		this.converterList = new LinkedList<IConverter>();

		for (IConverter converter : converters) {
			addConverter(converter);
		}

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

	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		ConverterException firstException = null;

		if (canReorder && converterList.size() > 1) {
			// try the first converter. If it is the good one, we will not
			// have to reorder the converters
			IConverter firstConverter = converterList.getFirst();
			if (firstConverter
					.canHandle(context, sourceObject, destinationType)) {
				try {
					return firstConverter.convert(context, sourceObject,
							destinationType);
				} catch (ConverterException e) {
					if (firstException == null)
						firstException = e;
				}
			}

			// we will have to reorder the converters, we need to make a copy of
			// the list, otherwise we would have a
			// ConcurrentModificationException (because this converter instance
			// can be used as an element converter)
			IConverter[] converters = converterList
					.toArray(new IConverter[converterList.size()]);

			for (int i = 1; i < converters.length; i++) {
				IConverter converter = converters[i];
				if (converter.canHandle(context, sourceObject, destinationType)) {
					try {
						Object result = converter.convert(context,
								sourceObject, destinationType);
						if (converterList.getFirst() != converter) {
							converterList.remove(converter);
							converterList.addFirst(converter);
						}
						return result;
					} catch (ConverterException e) {
						if (firstException == null)
							firstException = e;
					}
				}
			}
		} else {
			// don't reorder the converters
			for (IConverter converter : converterList) {
				if (converter.canHandle(context, sourceObject, destinationType)) {
					try {
						Object result = converter.convert(context,
								sourceObject, destinationType);
						return result;
					} catch (ConverterException e) {
						if (firstException == null)
							firstException = e;
					}
				}
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

	public void setElementConverter(IConverter elementConverter) {
		super.setElementConverter(elementConverter);

		for (IConverter converter : converterList) {
			if (converter instanceof IContainerConverter) {
				IContainerConverter containerConverter = (IContainerConverter) converter;
				if (containerConverter.getElementConverter() == null) {
					containerConverter
							.setElementConverter(this.elementConverter);
				}
			}
		}
	}

	@Override
	protected boolean canHandleDestinationType(Type destinationType) {
		return true;
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

	@Override
	protected boolean canBeAddedToUsedConverters() {
		// this would not add much value as this converter does no conversion by
		// itself. It just delegates its work to another converter.
		return false;
	}

}
