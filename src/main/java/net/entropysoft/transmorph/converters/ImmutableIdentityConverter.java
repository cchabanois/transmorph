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
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.utils.ImmutableClasses;

/**
 * Identity converter for immutable classes
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class ImmutableIdentityConverter extends AbstractConverter {

	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		return sourceObject;
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return ImmutableClasses.isKnownImmutableClass(destinationType.getType());
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		if (!super.canHandle(context, sourceObject, destinationType)) {
			return false;
		}
		try {
			if (sourceObject == null) {
				return true;
			}

			return destinationType.isInstance(sourceObject);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
