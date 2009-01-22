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

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.modifiers.IModifier;
import net.entropysoft.transmorph.modifiers.ModifierException;
import net.entropysoft.transmorph.type.Type;

/**
 * Abstract converter that adds the notion of modifiers to IConverter
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public abstract class AbstractConverter implements IConverter {
	private final static IModifier[] EMPTY_MODIFIERS = new IModifier[0];

	private IModifier[] modifiers = EMPTY_MODIFIERS;

	public Object convert(Object sourceObject, Type destinationType)
			throws ConverterException {
		Object result = doConvert(sourceObject, destinationType);
		result = applyModifiers(result, destinationType);
		return result;
	}

	public abstract Object doConvert(Object sourceObject, Type destinationType)
			throws ConverterException;

	protected Object applyModifiers(Object object, Type destinationType)
			throws ConverterException {
		Object initialObject = object;
		for (IModifier modifier : modifiers) {
			try {
				object = modifier.modify(object);
			} catch (ModifierException e) {
				throw new ConverterException(e.getMessage(), e);
			}
		}

		if (modifiers.length > 0 && object != initialObject && object != null) {
			try {
				if (!destinationType.isInstance(object)) {
					throw new ConverterException(
							"A modifier has modified the object type and it is no more compatible with expected destination type");
				}
			} catch (ClassNotFoundException e) {
				throw new ConverterException(
						"Could not verify type of the object modified by modifiers",
						e);
			}
		}
		return object;
	}

	public IModifier[] getModifiers() {
		return modifiers;
	}

	public void setModifiers(IModifier[] modifiers) {
		this.modifiers = modifiers;
	}

}
