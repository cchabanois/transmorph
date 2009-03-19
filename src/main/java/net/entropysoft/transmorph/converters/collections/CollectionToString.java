package net.entropysoft.transmorph.converters.collections;

import java.util.Collection;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.Type;

public class CollectionToString extends AbstractContainerConverter {

	private IStringArrayFormatter stringArrayFormatter = new DefaultStringArrayFormatter();	
	
	public CollectionToString() {
		this.useObjectPool = false;
	}	
	
	public IStringArrayFormatter getStringArrayFormatter() {
		return stringArrayFormatter;
	}

	public void setStringArrayFormatter(IStringArrayFormatter stringArrayFormatter) {
		this.stringArrayFormatter = stringArrayFormatter;
	}

	public Object doConvert(ConversionContext context, Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Collection<Object> collection = (Collection<Object>) sourceObject;

		String[] stringArray = new String[collection.size()];
		
		int i = 0;
		for (Object element : collection) {
			String elementConverted = (String)elementConverter.convert(
					context, element, destinationType);
			stringArray[i] = elementConverted;
			i++;
		}

		return stringArrayFormatter.format(stringArray);
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isType(String.class);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof Collection;
	}

}
