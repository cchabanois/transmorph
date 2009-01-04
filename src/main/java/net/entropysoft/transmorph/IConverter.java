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
package net.entropysoft.transmorph;

import net.entropysoft.transmorph.type.Type;

/**
 * Converters implement this interface
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public interface IConverter {

	/**
	 * Convert a source object to a destination type. This method will be called
	 * only if isHandled returned true.
	 * 
	 * @param elementConverter
	 * @param sourceObject
	 * @param destinationType
	 * @return
	 * @throws ConverterException
	 */
	public Object convert(IConverter elementConverter, Object sourceObject,
			Type destinationType) throws ConverterException;

	/**
	 * Check if this converter can convert to given destination type
	 * 
	 * @param destinationType
	 * @return
	 */
	public boolean canHandleDestinationType(Type destinationType);

	/**
	 * Check if this converter can convert from this source object
	 * 
	 * @param sourceObject
	 * @return
	 */
	public boolean canHandleSourceObject(Object sourceObject);

}
