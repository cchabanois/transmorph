package net.entropysoft.transmorph.converters;

import java.lang.reflect.Array;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter used to convert a single element to an array (with one element)
 * 
 * @author cedric
 * 
 */
public class SingleElementToArray extends AbstractContainerConverter {

	public Object doConvert(ConversionContext context, Object sourceObject, TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		TypeReference<?> elementType = destinationType.getArrayElementType();
		Class<?> destinationComponentType = elementType.getRawType();

		Object destinationArray = Array
				.newInstance(destinationComponentType, 1);

		Object elementConverted = elementConverter.convert(context,
				sourceObject, elementType);
		Array.set(destinationArray, 0, elementConverted);

		return destinationArray;
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		if (!destinationType.isArray()) {
			return false;
		}
		// we don't handle arrays with more than one dimension
		if (destinationType.getArrayNumDimensions() > 1) {
			return false;
		}
		return true;
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

}
