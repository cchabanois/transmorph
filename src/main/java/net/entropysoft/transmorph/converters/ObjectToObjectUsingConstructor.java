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

import java.lang.reflect.Constructor;
import java.text.MessageFormat;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter that converts an Object to another Object that has a constructor
 * that takes the object to be converted as parameter
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ObjectToObjectUsingConstructor extends AbstractConverter {

	public final static Class[] ALL_DESTINATION_CLASSES = null;

	private Class[] handledDestinationClasses = ALL_DESTINATION_CLASSES;

	public Class[] getHandledDestinationClasses() {
		return handledDestinationClasses;
	}

	public void setHandledDestinationClasses(Class[] handledDestinationClasses) {
		this.handledDestinationClasses = handledDestinationClasses;
	}

	public Object doConvert(Object sourceObject, Type destinationType) throws ConverterException {

		try {
			Constructor compatibleConstructor = getCompatibleConstructor(
					destinationType.getType(), sourceObject.getClass());

			if (compatibleConstructor == null) {
				throw new ConverterException(MessageFormat
						.format(
								"Could not convert ''{0}'' to destination type ''{1}''",
								sourceObject.getClass(), destinationType.getName()));
			}
			
			return compatibleConstructor
					.newInstance(new Object[] { sourceObject });
		} catch (Exception e) {
			throw new ConverterException(
					"Could not create an object with type '"
							+ destinationType.getName() + "' from object '"
							+ sourceObject.getClass().getName()
							+ "' using a constructor");
		}
	}

	/**
	 * Get a compatible constructor
	 * 
	 * @param type
	 *            Class to look for constructor in
	 * @param argumentType
	 *            Argument type for constructor
	 * @return the compatible constructor or null if none found
	 */
	public Constructor getCompatibleConstructor(Class type, Class argumentType) {
		try {
			return type.getConstructor(new Class[] { argumentType });
		} catch (Exception e) {
			// get public classes and interfaces
			Class[] types = type.getClasses();

			for (int i = 0; i < types.length; i++) {
				try {
					return type.getConstructor(new Class[] { types[i] });
				} catch (Exception e1) {
				}
			}
		}
		return null;
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		if (handledDestinationClasses == ALL_DESTINATION_CLASSES) {
			return true;
		}
		for (Class handledClass : handledDestinationClasses) {
			try {
				if (destinationType.isType(handledClass)) {
					return true;
				}
			} catch (ClassNotFoundException e) {
				return false;
			}
		}
		return false;
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

}
