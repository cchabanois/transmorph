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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * converter used when source object type and destination type are compatible
 * and source object is cloneable.
 * 
 * The source object is cloned.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class CloneableConverter extends AbstractConverter {

	public CloneableConverter() {
		this.useObjectPool = false;
	}

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isRawTypeSubOf(Cloneable.class);
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return sourceObject != null && sourceObject instanceof Cloneable;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		try {
			Method cloneableMethod = sourceObject.getClass().getMethod("clone");
			if (!Modifier.isPublic(cloneableMethod.getModifiers())) {
				throw new ConverterException(MessageFormat.format(
						"clone method for ''{0}''is not public", sourceObject
								.getClass().getName()));
			}
			return cloneableMethod.invoke(sourceObject);
		} catch (Exception e) {
			throw new ConverterException("Could not clone object with class '"
					+ sourceObject.getClass().getName() + "'");
		}
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		return super.canHandle(context, sourceObject, destinationType)
				&& destinationType.isRawTypeInstance(sourceObject);

	}

}
