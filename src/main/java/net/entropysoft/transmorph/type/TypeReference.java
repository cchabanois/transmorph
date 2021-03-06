/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.entropysoft.transmorph.type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.signature.formatter.ITypeSignatureFormatter;
import net.entropysoft.transmorph.signature.formatter.JavaSyntaxTypeSignatureFormatter;

/**
 * This class is based on Neal Gafter's Super Type Tokens to overcome type
 * erasure. (http://gafter.blogspot.com/2006/12/super-type-tokens.html)
 * 
 * Usage is by sub-classing: here is one way to instantiate reference to generic
 * type <code>List&lt;Integer></code>:
 * 
 * <pre>
 * TypeReference ref = new TypeReference&lt;List&lt;Integer&gt;&gt;() {
 * };
 *</pre>
 * 
 * We implement Comparable to ensure that a Type argument is given.
 * 
 * Some ideas and code come from gson (http://code.google.com/p/google-gson/)
 * and some from Jackson Json-processor (http://jackson.codehaus.org)
 * 
 * This class is thread-safe
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 * @param <T>
 */
public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {
	private final Type type;
	private final Class<? super T> rawType;
	private volatile TypeSignature typeSignature;

	@SuppressWarnings("unchecked")
	protected TypeReference() {
		this.type = getSuperclassTypeParameter(getClass());
		this.rawType = (Class<? super T>) TypeUtils.getRawType(type);
	}

	@SuppressWarnings("unchecked")
	private TypeReference(Type type) {
		this.type = type;
		this.rawType = (Class<? super T>) TypeUtils.getRawType(type);
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

	public boolean isArray() {
		if (type instanceof Class<?>) {
			return ((Class<?>) type).isArray();
		} else if (type instanceof GenericArrayType) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRawTypeInstance(Object object) {
		return rawType.isInstance(object);
	}

	public boolean isType(Type type) {
		return this.type == type;
	}

	public boolean hasRawType(Class<?> type) {
		return rawType == type;
	}

	public boolean isPrimitive() {
		return type == Byte.TYPE || type == Short.TYPE || type == Integer.TYPE
				|| type == Long.TYPE || type == Float.TYPE
				|| type == Double.TYPE || type == Character.TYPE
				|| type == Boolean.TYPE;
	}

	public boolean isRawTypeSubOf(Class<?> superClass) {
		return superClass.isAssignableFrom(rawType);
	}

	public boolean isAssignableFrom(TypeReference<?> typeReference) {
		return TypeUtils.isAssignableFrom(this, typeReference);
	}
	
	public TypeSignature getTypeSignature() {
		if (typeSignature == null) {
			typeSignature = TypeSignatureFactory.getTypeSignature(type);
		}
		return typeSignature;
	}

	/**
	 * check if type is a number type (either primitive or instance of Number)
	 * 
	 * @param type
	 * @return
	 * @throws ClassNotFoundException
	 */
	public boolean isNumber() {
		if (type == Byte.TYPE || type == Short.TYPE || type == Integer.TYPE
				|| type == Long.TYPE || type == Float.TYPE
				|| type == Double.TYPE) {
			return true;
		} else {
			return isRawTypeSubOf(Number.class);
		}
	}

	/**
	 * get a human representation for the type
	 * 
	 * @return
	 */
	public String toHumanString() {
		ITypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();
		return typeSignatureFormatter.format(getTypeSignature());
	}

	public TypeReference<?>[] getTypeArguments() {
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] typeArguments = parameterizedType.getActualTypeArguments();
			TypeReference<?>[] typeReferences = new TypeReference<?>[typeArguments.length];
			for (int i = 0; i < typeArguments.length; i++) {
				typeReferences[i] = TypeReference.get(typeArguments[i]);
			}
			return typeReferences;
		}
		return new TypeReference<?>[0];
	}

	public TypeReference<?> getArrayElementType() {
		if (!isArray()) {
			return null;
		}
		TypeReference<?> componentType = getArrayComponentType();
		while (componentType.isArray()) {
			componentType = componentType.getArrayComponentType();
		}
		return componentType;
	}

	public TypeReference<?> getArrayComponentType() {
		if (!isArray()) {
			return null;
		}
		if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;
			return TypeReference
					.get(genericArrayType.getGenericComponentType());
		}
		return TypeReference.get(rawType.getComponentType());
	}

	public int getArrayNumDimensions() {
		if (!isArray()) {
			return 0;
		}
		int numDim = 1;
		TypeReference<?> componentType = getArrayComponentType();
		while (componentType.isArray()) {
			componentType = componentType.getArrayComponentType();
			numDim++;
		}
		return numDim;
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

	private static class SimpleTypeReference<T> extends TypeReference<T> {
		public SimpleTypeReference(Type type) {
			super(type);
		}
	}

}