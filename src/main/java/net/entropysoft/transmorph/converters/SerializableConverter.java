package net.entropysoft.transmorph.converters;

import java.io.Serializable;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.utils.SerialClone;

/**
 * converter used when source object type and destination type are compatible
 * and source object is serializable.
 * 
 * The source object is cloned using serialization. Note that serialization is
 * hugely expensive.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class SerializableConverter extends AbstractConverter {

	public SerializableConverter() {
		this.useObjectPool = false;
	}

	@Override
	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isSubOf(Serializable.class);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return sourceObject == null || sourceObject instanceof Serializable;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		try {
			return SerialClone.clone(sourceObject);
		} catch (IllegalArgumentException e) {
			throw new ConverterException(
					"Could not clone object using serialization", e);
		}
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		try {
			return super.canHandle(context, sourceObject, destinationType)
					&& (sourceObject == null || destinationType
							.isInstance(sourceObject));
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

}
