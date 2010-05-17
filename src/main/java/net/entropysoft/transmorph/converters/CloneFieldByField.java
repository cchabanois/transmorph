package net.entropysoft.transmorph.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public class CloneFieldByField extends AbstractContainerConverter {

	private Objenesis objenesis = new ObjenesisStd();

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		Object targetObject = objenesis.newInstance(destinationType.getRawType());
		copyFieldByField(context, sourceObject, targetObject);
		return null;
	}

	private <T> void copyFieldByField(ConversionContext context, T from, T to) {
		Class<?> fromClass = from.getClass();
		while (fromClass != Object.class) {
			copyFieldValues(context, from, to, fromClass);
			fromClass = fromClass.getSuperclass();
		}
	}

	private <T> void copyFieldValues(ConversionContext context, T from, T to,
			Class<?> fromClass) {
		Field[] fields = fromClass.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			// ignore static fields
			Field field = fields[i];
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			AccessibilityChanger accessibilityChanger = new AccessibilityChanger();
			try {
				accessibilityChanger.enableAccess(field);
				Object sourceFieldValue = field.get(from);
				Object targetFieldValue = elementConverter.convert(context,
						sourceFieldValue, TypeReference.get(field.getGenericType()));
				field.set(to, targetFieldValue);
			} catch (Throwable t) {
				// Ignore - be lenient - if some field cannot be copied then
				// let's be it
			} finally {
				accessibilityChanger.safelyDisableAccess(field);
			}
		}
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		if (!super.canHandle(context, sourceObject, destinationType)) {
			return false;
		}
		return destinationType.isRawTypeInstance(sourceObject);
	}

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return true;
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

	public static class AccessibilityChanger {

		private Boolean wasAccessible = null;

		/**
		 * safely disables access
		 */
		public void safelyDisableAccess(Field field) {
			assert wasAccessible != null;
			try {
				field.setAccessible(wasAccessible);
			} catch (Throwable t) {
				// ignore
			}
		}

		/**
		 * changes the field accessibility and returns true if accessibility was
		 * changed
		 */
		public void enableAccess(Field field) {
			wasAccessible = field.isAccessible();
			field.setAccessible(true);
		}
	}

}
