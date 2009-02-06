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
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.context.ConvertedObjectPool;
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

	protected boolean useObjectPool = false;

	public Object convert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		Object convertedObject;
		ConvertedObjectPool objectPool = context.getConvertedObjectPool();
		if (useObjectPool) {
			convertedObject = objectPool.get(this, sourceObject,
					destinationType);
			if (convertedObject != null) {
				return convertedObject;
			}
		}
		try {
			convertedObject = doConvert(context, sourceObject, destinationType);
			convertedObject = applyModifiers(context, convertedObject,
					destinationType);
			if (useObjectPool) {
				objectPool.add(this, sourceObject, destinationType,
						convertedObject);
			}
			if (context.isStoreUsedConverters() && canBeAddedToUsedConverters()) {
				context.getUsedConverters().addUsedConverter(this,
						sourceObject, destinationType);
			}
			return convertedObject;
		} catch (ConverterException e) {
			if (useObjectPool) {
				objectPool.remove(this, sourceObject, destinationType);
			}
			throw e;
		} catch (RuntimeException e) {
			if (useObjectPool) {
				objectPool.remove(this, sourceObject, destinationType);
			}
			throw e;
		}
	}

	/**
	 * Generally when context.isStoreUsedConverters is true, a converter that
	 * has been used is added to the list of used converters. But for some
	 * converters this would just make the list longer without added value
	 * (this is the case for MultiConverter that delegates all its work to another converter)
	 * 
	 * @return
	 */
	protected boolean canBeAddedToUsedConverters() {
		return true;
	}

	public abstract Object doConvert(ConversionContext context,
			Object sourceObject, Type destinationType)
			throws ConverterException;

	protected Object applyModifiers(ConversionContext context, Object object,
			Type destinationType) throws ConverterException {
		Object initialObject = object;
		for (IModifier modifier : modifiers) {
			try {
				object = modifier.modify(context, object);
			} catch (ModifierException e) {
				throw new ConverterException(e.getMessage(), e);
			}
		}

		if (useObjectPool && object != initialObject) {
			if (context.getConvertedObjectPool().get(this, initialObject,
					destinationType) != null) {
				throw new ConverterException(
						"A modifier returned a different object than initial one whereas initial object has been added to object pool");
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

	public boolean isUseObjectPool() {
		return useObjectPool;
	}

	public void setUseObjectPool(boolean useObjectPool) {
		this.useObjectPool = useObjectPool;
	}

	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		return canHandleDestinationType(destinationType)
				&& canHandleSourceObject(sourceObject);
	}

	protected abstract boolean canHandleDestinationType(Type destinationType);

	protected abstract boolean canHandleSourceObject(Object sourceObject);

}
