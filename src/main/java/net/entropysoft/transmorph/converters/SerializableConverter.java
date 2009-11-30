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

import java.io.Serializable;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;
import net.entropysoft.transmorph.utils.SerialClone;

/**
 * converter used when source object type and destination type are compatible
 * and source object is serializable.
 * 
 * The source object is cloned using serialization. Note that serialization is
 * hugely expensive.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class SerializableConverter extends AbstractConverter {

	public SerializableConverter() {
		this.useObjectPool = false;
	}

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isRawTypeSubOf(Serializable.class);
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return sourceObject == null || sourceObject instanceof Serializable;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		try {
			return SerialClone.clone(sourceObject);
		} catch (IllegalArgumentException e) {
			throw new ConverterException(
					"Could not clone object using serialization", e);
		}
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		return super.canHandle(context, sourceObject, destinationType)
				&& (sourceObject == null || destinationType
						.isRawTypeInstance(sourceObject));
	}

}
