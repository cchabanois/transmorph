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

import net.entropysoft.transmorph.IContainerConverter;
import net.entropysoft.transmorph.IConverter;

/**
 * Abstract simple container converter used to convert from an object of a given Class to
 * an object of another Class
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 * @param <S>
 *            The source
 * @param <D>
 *            The destination
 */
public abstract class AbstractSimpleContainerConverter<S,D> extends
		AbstractSimpleConverter<S, D> implements IContainerConverter {

	protected AbstractSimpleContainerConverter(Class<S> sourceClass,
			Class<D> destinationClass) {
		super(sourceClass, destinationClass);
	}

	protected IConverter elementConverter;

	public IConverter getElementConverter() {
		return elementConverter;
	}

	public void setElementConverter(IConverter elementConverter) {
		this.elementConverter = elementConverter;
	}


}
