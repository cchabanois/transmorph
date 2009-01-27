package net.entropysoft.transmorph.converters;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.ClassType;
import net.entropysoft.transmorph.type.Type;

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

	public Object doConvert(ConversionContext context, Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		ClassType collectionClassType = (ClassType) destinationType;
		Collection<Object> destinationCollection;
		try {
			destinationCollection = createDestinationCollection(destinationType);
		} catch (Exception e) {
			throw new ConverterException(
					"Could not create destination collection", e);
		}

		Type[] destinationTypeArguments = collectionClassType
				.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new Type[] { destinationType
					.getTypeFactory().getObjectType() };
		}

		Object convertedObj = elementConverter.convert(context,
				sourceObject, destinationTypeArguments[0]);
		destinationCollection.add(convertedObj);
		return destinationCollection;

	}

	private Collection<Object> createDestinationCollection(Type destinationType)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Class<? extends Collection> clazz = getConcreteCollectionDestinationClass(destinationType);
		return clazz.newInstance();
	}

	protected Class<? extends Collection> getConcreteCollectionDestinationClass(
			Type destinationType) throws ClassNotFoundException {
		if (destinationType.isType(Set.class)) {
			return defaultSetClass;
		}
		if (destinationType.isType(List.class)) {
			return defaultListClass;
		}
		if (destinationType.isType(Collection.class)) {
			return defaultListClass;
		}
		Class destinationClass = destinationType.getType();
		if (destinationClass.isInterface()
				|| Modifier.isAbstract(destinationClass.getModifiers())) {
			return null;
		}
		try {
			destinationClass.getConstructor(new Class[0]);
		} catch (Exception e) {
			return null;
		}
		return destinationClass;
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isSubOf(Collection.class);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

}
