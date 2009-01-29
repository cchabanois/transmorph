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

/**
 * <p>
 * Some converters needs to convert elements from the source object to elements
 * of the destination object. This is the case for converters handling
 * collections, arrays, or beans.
 * </p>
 * <p>
 * If the element converter is not set by the user, it will be set by
 * {@link Converter} constructor to the created Converter
 * </p>
 * <p>
 * If set, the element converter allows you to use different converters to
 * convert elements than the converters used for converting the containing
 * object.
 * </p>
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public interface IContainerConverter extends IConverter {

	/**
	 * Set the element converter.
	 * 
	 * @param elementConverter
	 */
	public void setElementConverter(IConverter elementConverter);

	/**
	 * Get the element converter.
	 * 
	 * @return
	 */
	public IConverter getElementConverter();

}
