package net.entropysoft.transmorph.signature;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.reflect.GenericArrayTypeImpl;
import net.entropysoft.transmorph.reflect.ParameterizedTypeImpl;
import net.entropysoft.transmorph.reflect.WildcardTypeImpl;

public class TypeFactory {
	private static Map<Character, Class> primitiveTypesMap = new HashMap<Character, Class>();

	static {
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_BOOLEAN,
				Boolean.TYPE);
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_BYTE, Byte.TYPE);
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_CHAR,
				Character.TYPE);
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_DOUBLE,
				Double.TYPE);
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_FLOAT,
				Float.TYPE);
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_INT,
				Integer.TYPE);
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_LONG, Long.TYPE);
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_SHORT,
				Short.TYPE);
	}

	private ClassLoader classLoader;

	public TypeFactory(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public Type getType(TypeSignature typeSignature)
			throws ClassNotFoundException {
		if (typeSignature.isPrimitiveType()) {
			return getType((PrimitiveTypeSignature) typeSignature);
		}
		if (typeSignature.isArrayType()) {
			return getType((ArrayTypeSignature) typeSignature);
		}
		if (typeSignature.isClassType()) {
			return getType((ClassTypeSignature) typeSignature);
		}
		if (typeSignature.isTypeVar()) {
			throw new ClassNotFoundException("Type variable not supported");
		}
		throw new ClassNotFoundException("Signature type not supported");
	}

	public Type getType(PrimitiveTypeSignature primitiveTypeSignature) {
		return primitiveTypesMap.get(primitiveTypeSignature
				.getPrimitiveTypeChar());
	}

	public GenericArrayType getType(ArrayTypeSignature arrayTypeSignature)
			throws ClassNotFoundException {
		Type componentType = getType(arrayTypeSignature
				.getComponentTypeSignature());
		return new GenericArrayTypeImpl(componentType);
	}

	public Type getType(ClassTypeSignature classTypeSignature)
			throws ClassNotFoundException {
		TypeArgSignature[] typeArgSignatures = classTypeSignature
				.getTypeArgSignatures();
		if (typeArgSignatures.length == 0) {
			return getClass(classTypeSignature);
		}
		Type rawType = getType(classTypeSignature.getTypeErasureSignature());
		Type ownerType;
		if (classTypeSignature.getOwnerTypeSignature() == null) {
			ownerType = null;
		} else {
			ownerType = getType(classTypeSignature.getOwnerTypeSignature());
		}

		Type[] actualTypeArguments = new Type[typeArgSignatures.length];
		for (int i = 0; i < actualTypeArguments.length; i++) {
			actualTypeArguments[i] = getType(typeArgSignatures[i]);
		}
		return new ParameterizedTypeImpl(rawType, actualTypeArguments,
				ownerType);
	}

	private Class<?> getClass(ClassTypeSignature typeErasureSignature)
			throws ClassNotFoundException {
		String className = typeErasureSignature.getClassName();
		return Class.forName(className, true, classLoader);
	}

	private Type getType(TypeArgSignature typeArgSignature)
			throws ClassNotFoundException {
		char wildcard = typeArgSignature.getWildcard();
		if (wildcard == TypeArgSignature.NO_WILDCARD) {
			return getType(typeArgSignature.getFieldTypeSignature());
		}
		if (wildcard == TypeArgSignature.UNBOUNDED_WILDCARD) {
			return new WildcardTypeImpl(new Type[] { Object.class },
					new Type[0]);
		}
		if (wildcard == TypeArgSignature.LOWERBOUND_WILDCARD) {
			return new WildcardTypeImpl(new Type[] { Object.class },
					new Type[] { getType(typeArgSignature
							.getFieldTypeSignature()) });
		}
		if (wildcard == TypeArgSignature.UPPERBOUND_WILDCARD) {
			return new WildcardTypeImpl(new Type[] { getType(typeArgSignature
					.getFieldTypeSignature()) }, new Type[0]);
		}
		throw new ClassNotFoundException(
				"Invalid wildcard in type arg signature");
	}
}
