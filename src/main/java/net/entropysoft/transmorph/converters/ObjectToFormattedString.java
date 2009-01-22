package net.entropysoft.transmorph.converters;

import java.text.Format;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used to convert an object to a formatted string
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ObjectToFormattedString extends AbstractContainerConverter {

	private Format format;
	private Class sourceClass;
	private Class formatExpectedClass;
	private IConverter converter;

	/**
	 * 
	 * @param sourceClass
	 *            class of the objects we want to format. Must be supported by given Format
	 * @param format
	 *            format to use
	 */
	public ObjectToFormattedString(Class sourceClass, Format format) {
		this.sourceClass = sourceClass;
		this.formatExpectedClass = sourceClass;
		this.format = format;
	}

	/**
	 * 
	 * @param sourceClass
	 *            the class of the object we want to format
	 * @param formatExpectedClass
	 *            class supported by given Format
	 * @param format
	 *            format to use
	 */
	public ObjectToFormattedString(Class sourceClass,
			Class formatExpectedClass, Format format) {
		this.sourceClass = sourceClass;
		this.formatExpectedClass = formatExpectedClass;
		this.format = format;
	}

	public Object doConvert(Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Object formatSource = sourceObject;
		if (sourceClass != formatExpectedClass) {
			formatSource = elementConverter.convert(sourceObject,
					destinationType.getTypeFactory().getType(
							formatExpectedClass));
		}
		synchronized(this) {
			return format.format(formatSource);
		}
	}

	public boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isType(String.class);
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

}
