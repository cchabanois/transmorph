package net.entropysoft.transmorph.converters;

import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.beans.utils.ClassPair;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter that forces the use of a given converter for an object of a given
 * class to an object of a given destination class.
 * 
 * @author cedric
 * 
 */
public class StaticConverter extends AbstractConverter {
	private final Map<ClassPair<?, ?>, IConverter> converters = new HashMap<ClassPair<?, ?>, IConverter>();

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return true;
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		if (!super.canHandle(context, sourceObject, destinationType)) {
			return false;
		}
		if (sourceObject == null) {
			return true;
		}
		IConverter converter = getConverter(sourceObject.getClass(),
				destinationType.getRawType());
		if (converter == null) {
			return false;
		} else {
			return converter.canHandle(context, sourceObject, destinationType);
		}

	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		IConverter converter = getConverter(sourceObject.getClass(),
				destinationType.getRawType());
		return converter.convert(context, sourceObject, destinationType);
	}

	private IConverter getConverter(Class<?> source, Class<?> dest) {
		ClassPair<?, ?> classPair = ClassPair.get(source, dest);
		return converters.get(classPair);
	}

	public void addConverter(Class<?> source, Class<?> dest,
			IConverter converter) {
		ClassPair<?, ?> classPair = ClassPair.get(source, dest);
		converters.put(classPair, converter);
	}

}
