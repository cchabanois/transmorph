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
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Abstract simple converter used to convert from an object of a given Class to
 * an object of another Class
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 * @param <S>
 *            The source
 * @param <D>
 *            The destination
 */
public abstract class AbstractSimpleConverter<S, D> implements IConverter {
	private final static IModifier[] EMPTY_MODIFIERS = new IModifier[0];

	private IModifier<D>[] modifiers = EMPTY_MODIFIERS;
	private Class<S> sourceClass;
	private Class<D> destinationClass;
	protected boolean useObjectPool = false;

	protected AbstractSimpleConverter(Class<S> sourceClass, Class<D> destinationClass) {
		this.sourceClass = sourceClass;
		this.destinationClass = destinationClass;
	}

	public Class<S> getSourceClass() {
		return sourceClass;
	}

	public Class<D> getDestinationClass() {
		return destinationClass;
	}

	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		return canHandleDestinationType(destinationType)
				&& canHandleSourceObject(sourceObject);
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isType(destinationClass);
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceClass.isInstance(sourceObject);
	}

	public Object convert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException(
						"Cannot convert null to a primitive type");
			}
		}
		D convertedObject;
		ConvertedObjectPool objectPool = context.getConvertedObjectPool();
		if (useObjectPool) {
			convertedObject = (D)objectPool.get(this, sourceObject,
					destinationType);
			if (convertedObject != null) {
				return convertedObject;
			}
		}
		try {
			convertedObject = doConvert(context, (S) sourceObject,
					destinationType);
			convertedObject = applyModifiers(context, convertedObject, destinationType);
			if (useObjectPool) {
				objectPool.add(this, sourceObject, destinationType,
						convertedObject);
			}
			if (context.isStoreUsedConverters()) {
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

	public abstract D doConvert(ConversionContext context, S sourceObject,
			TypeReference<?> destinationType) throws ConverterException;

	protected D applyModifiers(ConversionContext context, D object, TypeReference<?> destinationType) throws ConverterException {
		Object initialObject = object;
		for (IModifier<D> modifier : modifiers) {
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
		
		return object;
	}

	public IModifier<D>[] getModifiers() {
		return modifiers;
	}

	public void setModifiers(IModifier<D>[] modifiers) {
		this.modifiers = modifiers;
	}

}
