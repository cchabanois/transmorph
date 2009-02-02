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
	 * only if canHandle returned true.
	 * 
	 * @param context
	 * @param sourceObject
	 * @param destinationType
	 * 
	 * @return the converted object
	 * @throws ConverterException
	 */
	public Object convert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException;

	/**
	 * Check if this converter can handle the conversion between given source
	 * object and given destination type. Note that it does not mean that
	 * conversion will succeed but that it is worth to try
	 * 
	 * @param context
	 * @param destinationType
	 * 
	 * @return true if convert can be call to try a conversion
	 */
	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType);

}
