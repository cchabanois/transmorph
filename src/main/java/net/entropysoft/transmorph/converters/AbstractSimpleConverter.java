package net.entropysoft.transmorph.converters;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Abstract simple converter 
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 * @param <S> The source
 * @param <D> The destination
 */
public abstract class AbstractSimpleConverter<S,D> implements IConverter {
	private Class sourceClass;
	private Class destinationClass;
	
	public AbstractSimpleConverter(Class sourceClass, Class destinationClass) {
		this.sourceClass = sourceClass;
		this.destinationClass = destinationClass;
	}
	
	public Class getSourceClass() {
		return sourceClass;
	}

	public Class getDestinationClass() {
		return destinationClass;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isType(destinationClass);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceClass.isInstance(sourceObject);
	}

	public Object convert(IConverter elementConverter, Object sourceObject,
			Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException("Cannot convert null to a primitive type");
			}
		}
		return doConvert(elementConverter, (S)sourceObject, destinationType);
	}

	public abstract D doConvert(IConverter elementConverter, S sourceObject,
			Type destinationType) throws ConverterException; 
	
}
