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
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter to convert from one type to another using multiple intermediate
 * types
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MultiStepConverter extends AbstractContainerConverter {

	private final TypeReference<?>[] types;
	private IConverter[] stepConverters;

	/**
	 * 
	 * @param types
	 *            first one is the source type. Last one is the destination
	 *            type. Others are intermediate types
	 * @param stepConverters
	 *            the converters to use for each step. Note that there is one
	 *            less step than types
	 * 
	 */
	public MultiStepConverter(TypeReference<?>[] types, IConverter[] stepConverters) {
		this.types = types;
		setStepConverters(stepConverters);
	}

	/**
	 * 
	 * @param types
	 *            first one is the source type. Last one is the destination
	 *            type. Others are intermediate types
	 */
	public MultiStepConverter(TypeReference<?>[] types) {
		this.types = types;
	}

	public void setStepConverters(IConverter[] stepConverters) {
		if (stepConverters.length != (types.length - 1)) {
			throw new IllegalArgumentException(
					"stepConverters does not have correct length. It must have one less element than types");
		}
		this.stepConverters = stepConverters;
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.equals(types[types.length - 1]);
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return types[0].isRawTypeInstance(sourceObject);
	}

	public Object doConvert(ConversionContext context, Object sourceObject, TypeReference<?> destinationType)
			throws ConverterException {
		Object stepSourceObject = sourceObject;
		for (int i = 1; i < types.length; i++) {
			IConverter stepConverter = stepConverters == null ? elementConverter
					: stepConverters[i - 1];
			stepSourceObject = stepConverter
					.convert(context, stepSourceObject, types[i]);
		}
		return stepSourceObject;
	}

}
