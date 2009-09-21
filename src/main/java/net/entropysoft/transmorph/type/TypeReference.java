package net.entropysoft.transmorph.type;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import net.entropysoft.transmorph.reflect.ParameterizedTypeImpl;
import net.entropysoft.transmorph.signature.ArrayTypeSignature;
import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.Signature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;

public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {
	private final Type type;
	private final Class<? super T> rawType;
	private final Signature typeSignature;

	@SuppressWarnings("unchecked")
	protected TypeReference() {
		this.type = getSuperclassTypeParameter(getClass());
		this.rawType = (Class<? super T>) getRawType(type);
		this.typeSignature = TypeSignatureFactory.getTypeSignature(type);
	}

	@SuppressWarnings("unchecked")
	private TypeReference(Type type) {
		this.type = type;
		this.rawType = (Class<? super T>) getRawType(type);
		this.typeSignature = TypeSignatureFactory.getTypeSignature(type);
	}

	/**
	 * Gets type token for the given {@code Class} instance.
	 */
	public static <T> TypeReference<T> get(Class<T> type) {
		return new SimpleTypeReference<T>(type);
	}

	/**
	 * Gets type token for the given {@code Type} instance.
	 */
	public static TypeReference<?> get(Type type) {
		return new SimpleTypeReference<Object>(type);
	}

	public static TypeReference<?> get(Class<?> clazz, Class<?>[] typeArgs) {
		return get(new ParameterizedTypeImpl(clazz, typeArgs, null));
	}
	
	public Type getType() {
		return type;
	}

	public Class<? super T> getRawType() {
		return rawType;
	}

	public Signature getTypeSignature() {
		return typeSignature;
	}
	
	public boolean isArray() {
		return typeSignature.isArrayType();
	}

	public boolean isInstance(Object object) {
		return rawType.isInstance(object);
	}

	public boolean isType(Type type) {
		return this.type.equals(type);
	}
	
	public boolean hasRawType(Class<?> type) {
		return this.rawType.equals(type);
	}
	
	public boolean isPrimitive() {
		return typeSignature.isPrimitiveType();
	}

	public boolean isSubOf(Class<?> superClass) {
		return superClass.isAssignableFrom(rawType);
	}

	/**
	 * check if type is a number type (either primitive or instance of Number)
	 * @param type
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public boolean isNumber() {
		if (isPrimitive()) {
			return  ((PrimitiveTypeSignature)typeSignature).isNumber();
		} else {
			return isSubOf(Number.class);
		}
	}	
	
	/**
	 * get the type name (similar to Class.getName())
	 * 
	 * @return
	 */
	public String getName() {
//		ClassGetNameSignatureFormatter classGetNameSignatureFormatter = new ClassGetNameSignatureFormatter();
//		return classGetNameSignatureFormatter.format(typeSignature);
		return typeSignature.getSignature();
	}	
	
	public TypeReference<?>[] getTypeArguments() {
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType)type;
			Type[] typeArguments = parameterizedType.getActualTypeArguments();
			TypeReference<?>[] typeReferences = new TypeReference<?>[typeArguments.length];
			for (int i = 0; i < typeArguments.length;i++) {
				typeReferences[i] = TypeReference.get(typeArguments[i]);
			}
			return typeReferences;
		}
		if (type instanceof WildcardType) {
			// TODO
		}
		return new TypeReference<?>[0];
	}
	
	public TypeReference<?> getArrayElementType() {
		if (!isArray()) {
			return null;
		}
		TypeReference<?> componentType = getArrayComponentType();
		while(componentType.isArray()) {
			componentType = componentType.getArrayComponentType();
		}
		return componentType;
	}
	
	public TypeReference<?> getArrayComponentType() {
		if (!isArray()) {
			return null;
		}
		if (type instanceof GenericArrayType)  {
			GenericArrayType genericArrayType = (GenericArrayType)type;
			return TypeReference.get(genericArrayType.getGenericComponentType());
		}
		return TypeReference.get(rawType.getComponentType());
	}

	public int getArrayNumDimensions() {
		if (typeSignature.isArrayType()) {
			ArrayTypeSignature arrayTypeSignature = (ArrayTypeSignature)typeSignature;
			return arrayTypeSignature.getNumDimensions();
		} else {
			return 0;
		}
	}	
	
	/**
	 * The only reason we define this method (and require implementation of
	 * <code>Comparable</code>) is to prevent constructing a reference without
	 * type information.
	 */
	public int compareTo(TypeReference<T> o) {
		return toString().compareTo(o.toString());
	}

	/**
	 * Hashcode for this object.
	 * 
	 * @return hashcode for this object.
	 */
	@Override
	public int hashCode() {
		return type.hashCode();
	}

	/**
	 * Method to test equality.
	 * 
	 * @return true if this object is logically equal to the specified object,
	 *         false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof TypeReference<?>)) {
			return false;
		}
		TypeReference<?> t = (TypeReference<?>) o;
		return type.equals(t.type);
	}

	/**
	 * Returns a string representation of this object.
	 * 
	 * @return a string representation of this object.
	 */
	@Override
	public String toString() {
		return type.toString();
	}

	/**
	 * Gets type from super class's type parameter.
	 */
	@SuppressWarnings("unchecked")
	public static Type getSuperclassTypeParameter(Class<?> subclass) {
		Type superclass = subclass.getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		return ((ParameterizedType) superclass).getActualTypeArguments()[0];
	}

	/**
	 * This method returns the actual raw class associated with the specified
	 * type.
	 */
	@SuppressWarnings("unchecked")
	public static Class<?> getRawType(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			ParameterizedType actualType = (ParameterizedType) type;
			return getRawType(actualType.getRawType());
		} else if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;
			Object rawArrayType = Array.newInstance(getRawType(genericArrayType
					.getGenericComponentType()), 0);
			return rawArrayType.getClass();
		} else if (type instanceof WildcardType) {
			WildcardType castedType = (WildcardType) type;
			return getRawType(castedType.getUpperBounds()[0]);
		} else {
			throw new IllegalArgumentException(
					"Type \'"
							+ type
							+ "\' is not a Class, "
							+ "ParameterizedType, or GenericArrayType. Can't extract class.");
		}
	}

	private static class SimpleTypeReference<T> extends TypeReference<T> {
		public SimpleTypeReference(Type type) {
			super(type);
		}
	}

}