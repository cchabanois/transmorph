package net.entropysoft.transmorph.converters;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter used to convert a single element to a collection (with one element)
 * 
 * @author cedric
 * 
 */
public class SingleElementToCollection extends AbstractContainerConverter {

	private Class<? extends Set> defaultSetClass = HashSet.class;
	private Class<? extends List> defaultListClass = ArrayList.class;

	public Class<? extends Set> getDefaultSetClass() {
		return defaultSetClass;
	}

	public void setDefaultSetClass(Class<? extends Set> defaultSetClass) {
		this.defaultSetClass = defaultSetClass;
	}

	public Class<? extends List> getDefaultListClass() {
		return defaultListClass;
	}

	public void setDefaultListClass(Class<? extends List> defaultListClass) {
		this.defaultListClass = defaultListClass;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		Collection<Object> destinationCollection;
		try {
			destinationCollection = createDestinationCollection(destinationType);
		} catch (Exception e) {
			throw new ConverterException(
					"Could not create destination collection", e);
		}

		TypeReference<?>[] destinationTypeArguments = destinationType.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new TypeReference<?>[] { TypeReference.get(Object.class) };
		}

		Object convertedObj = elementConverter.convert(context, sourceObject,
				destinationTypeArguments[0]);
		destinationCollection.add(convertedObj);
		return destinationCollection;

	}

	private Collection<Object> createDestinationCollection(
			TypeReference<?> destinationType) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class<? extends Collection> clazz = getConcreteCollectionDestinationClass(destinationType);
		return clazz.newInstance();
	}

	protected Class<? extends Collection> getConcreteCollectionDestinationClass(
			TypeReference<?> destinationType) throws ClassNotFoundException {
		if (destinationType.hasRawType(Set.class)) {
			return defaultSetClass;
		}
		if (destinationType.hasRawType(List.class)) {
			return defaultListClass;
		}
		if (destinationType.hasRawType(Collection.class)) {
			return defaultListClass;
		}
		Class<?> destinationClass = (Class<?>) destinationType.getRawType();
		if (destinationClass.isInterface()
				|| Modifier.isAbstract(destinationClass.getModifiers())) {
			return null;
		}
		try {
			destinationClass.getConstructor(new Class[0]);
		} catch (Exception e) {
			return null;
		}
		return (Class<? extends Collection>)destinationClass;
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isRawTypeSubOf(Collection.class);

	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

}
