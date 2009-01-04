package net.entropysoft.transmorph.converters;

import java.lang.reflect.Array;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.ArrayType;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used to convert a single element to an array (with one element)
 * 
 * @author cedric
 * 
 */
public class SingleElementToArray implements IConverter {

	public Object convert(IConverter elementConverter, Object sourceObject,
			Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		ArrayType arrayDestinationType = (ArrayType) destinationType;

		Type elementType = arrayDestinationType.getElementType();
		Class destinationComponentType;
		try {
			destinationComponentType = elementType.getType();
		} catch (ClassNotFoundException e) {
			throw new ConverterException("Could not find class for "
					+ elementType.getName());
		}

		Object destinationArray = Array
				.newInstance(destinationComponentType, 1);

		Object elementConverted = elementConverter.convert(elementConverter,
				sourceObject, elementType);
		Array.set(destinationArray, 0, elementConverted);

		return destinationArray;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		if (!destinationType.isArray()) {
			return false;
		}
		// we don't handle arrays with more than one dimension
		if (((ArrayType) destinationType).getNumDimensions() > 1) {
			return false;
		}
		return true;
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

}
