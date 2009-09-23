package net.entropysoft.transmorph.signature;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

/**
 * get the type signature of a java type
 * 
 * @author cedric
 * 
 */
public class JavaTypeToTypeSignature {

	private static Map<Class<?>, Character> primitiveTypesMap = new HashMap<Class<?>, Character>();

	static {
		primitiveTypesMap.put(Boolean.TYPE,
				PrimitiveTypeSignature.PRIMITIVE_BOOLEAN);
		primitiveTypesMap.put(Byte.TYPE, PrimitiveTypeSignature.PRIMITIVE_BYTE);
		primitiveTypesMap.put(Character.TYPE,
				PrimitiveTypeSignature.PRIMITIVE_CHAR);
		primitiveTypesMap.put(Double.TYPE,
				PrimitiveTypeSignature.PRIMITIVE_DOUBLE);
		primitiveTypesMap.put(Float.TYPE,
				PrimitiveTypeSignature.PRIMITIVE_FLOAT);
		primitiveTypesMap.put(Integer.TYPE,
				PrimitiveTypeSignature.PRIMITIVE_INT);
		primitiveTypesMap.put(Long.TYPE, PrimitiveTypeSignature.PRIMITIVE_LONG);
		primitiveTypesMap.put(Short.TYPE,
				PrimitiveTypeSignature.PRIMITIVE_SHORT);
	}

	/**
	 * get the ArrayTypeSignature corresponding to given generic array type
	 * 
	 * @param genericArrayType
	 * @return
	 */
	private ArrayTypeSignature getArrayTypeSignature(
			GenericArrayType genericArrayType) {
		TypeSignature componentTypeSignature = getTypeSignature(genericArrayType
				.getGenericComponentType());
		ArrayTypeSignature arrayTypeSignature = new ArrayTypeSignature(
				componentTypeSignature);
		return arrayTypeSignature;
	}

	/**
	 * get the ClassTypeSignature corresponding to given parameterized type
	 * 
	 * @param parameterizedType
	 * @return
	 */
	private ClassTypeSignature getClassTypeSignature(
			ParameterizedType parameterizedType) {
		Class<?> rawType = (Class<?>) parameterizedType.getRawType();
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[typeArguments.length];
		for (int i = 0; i < typeArguments.length; i++) {
			typeArgSignatures[i] = getTypeArgSignature(typeArguments[i]);
		}

		String binaryName = rawType.isMemberClass() ? rawType.getSimpleName()
				: rawType.getName();
		ClassTypeSignature ownerTypeSignature = parameterizedType
				.getOwnerType() == null ? null
				: (ClassTypeSignature) getTypeSignature(parameterizedType
						.getOwnerType());
		ClassTypeSignature classTypeSignature = new ClassTypeSignature(
				binaryName, typeArgSignatures, ownerTypeSignature);
		return classTypeSignature;
	}

	/**
	 * get the type signature corresponding to given java type
	 * 
	 * @param type
	 * @return
	 */	
	public Signature getSignature(Type type) {
		TypeSignature typeSignature = getTypeSignature(type);
		if (typeSignature != null) {
			return typeSignature;
		}
		if (type instanceof WildcardType) {
			return getTypeArgSignature((WildcardType) type);
		}
		return null;
	}
	
	/**
	 * get the type signature corresponding to given java type
	 * 
	 * @param type
	 * @return
	 */
	private TypeSignature getTypeSignature(Type type) {
		if (type instanceof Class<?>) {
			return getTypeSignature((Class<?>) type);
		}
		if (type instanceof GenericArrayType) {
			return getArrayTypeSignature((GenericArrayType) type);
		}
		if (type instanceof ParameterizedType) {
			return getClassTypeSignature((ParameterizedType) type);
		}
		return null;
	}

	/**
	 * get the type signature corresponding to given class
	 * 
	 * @param clazz
	 * @return
	 */
	private TypeSignature getTypeSignature(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		if (clazz.isArray()) {
			sb.append(clazz.getName());
		} else if (clazz.isPrimitive()) {
			sb.append(primitiveTypesMap.get(clazz).toString());
		} else {
			sb.append('L').append(clazz.getName()).append(';');
		}
		return TypeSignatureFactory.getTypeSignature(sb.toString(), false);

	}

	/**
	 * get the TypeArgSignature corresponding to given type
	 * 
	 * @param type
	 * @return
	 */
	private TypeArgSignature getTypeArgSignature(Type type) {
		if (type instanceof WildcardType) {
			WildcardType wildcardType = (WildcardType) type;
			Type lowerBound = wildcardType.getLowerBounds().length == 0 ? null
					: wildcardType.getLowerBounds()[0];
			Type upperBound = wildcardType.getUpperBounds().length == 0 ? null
					: wildcardType.getUpperBounds()[0];

			if (lowerBound == null && Object.class.equals(upperBound)) {
				return new TypeArgSignature(
						TypeArgSignature.UNBOUNDED_WILDCARD,
						(FieldTypeSignature) getTypeSignature(upperBound));
			} else if (lowerBound == null && upperBound != null) {
				return new TypeArgSignature(
						TypeArgSignature.UPPERBOUND_WILDCARD,
						(FieldTypeSignature) getTypeSignature(upperBound));
			} else if (lowerBound != null) {
				return new TypeArgSignature(
						TypeArgSignature.LOWERBOUND_WILDCARD,
						(FieldTypeSignature) getTypeSignature(lowerBound));
			} else {
				throw new RuntimeException("Invalid type");
			}
		} else {
			return new TypeArgSignature(TypeArgSignature.NO_WILDCARD,
					(FieldTypeSignature) getTypeSignature(type));
		}
	}

}
