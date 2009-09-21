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
 * Converter used when destination is a String. It uses toString() method on the
 * source object
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ObjectToString extends AbstractConverter {

	public final static Class[] ALL_SOURCE_CLASSES = new Class[] { Object.class };

	private Class[] handledSourceClasses = ALL_SOURCE_CLASSES;
	private boolean failIfDefaultObjectToString = true;

	public ObjectToString() {
		this.useObjectPool = false;
	}

	public Class[] getHandledSourceClasses() {
		return handledSourceClasses;
	}

	public void setHandledSourceClasses(Class[] handledSourceClasses) {
		this.handledSourceClasses = handledSourceClasses;
	}

	public void setFailIfDefaultObjectToString(
			boolean failIfDefaultObjectToString) {
		this.failIfDefaultObjectToString = failIfDefaultObjectToString;
	}

	public boolean isFailIfDefaultObjectToString() {
		return failIfDefaultObjectToString;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		String result = sourceObject.toString();
		if (failIfDefaultObjectToString
				&& result.equals(getDefaultObjectToString(sourceObject))) {
			throw new ConverterException(
					"Cannot convert to string : toString() method has not been overridden");
		}

		return result;
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isType(String.class);
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		for (Class handledClass : handledSourceClasses) {
			if (handledClass.isAssignableFrom(sourceObject.getClass())) {
				return true;
			}
		}
		return false;
	}

	private String getDefaultObjectToString(Object object) {
		return object.getClass().getName() + "@"
				+ Integer.toHexString(object.hashCode());
	}

}
