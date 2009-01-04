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

import net.entropysoft.transmorph.signature.ClassFactory;
import net.entropysoft.transmorph.signature.TypeSignature;

/**
 * Represents a type, possibly parameterized
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public abstract class Type {
	protected TypeSignature typeSignature;
	protected TypeFactory typeFactory;

	private Class javaClass;
	private Type typeErasureSignature;
	
	public Type(TypeFactory typeFactory, TypeSignature typeSignature) {
		this.typeFactory = typeFactory;
		this.typeSignature = typeSignature;
	}

	public TypeSignature getTypeSignature() {
		return typeSignature;
	}

	public TypeFactory getTypeFactory() {
		return typeFactory;
	}

	/**
	 * Extracts the type erasure signature from the given parameterized type
	 * signature. Returns the given type signature if it is not parameterized.
	 * 
	 * @return
	 */
	public Type getTypeErasureSignature() {
		if (typeErasureSignature == null) {
			typeErasureSignature = typeFactory.getType(typeSignature.getTypeErasureSignature());
		}
		return typeErasureSignature;
	}

	public ClassFactory getClassFactory() {
		return typeFactory.getClassFactory();
	}
	
	public ClassLoader getClassLoader() {
		return getClassFactory().getClassLoader();
	}
	
	/**
	 * Returns true if this class is a superclass or superinterface of the class
	 * represented by subClassFQN
	 * 
	 * @param subClassFQN
	 * @return
	 * @throws JavaModelException
	 */
	public boolean isSuperOf(Class subClass) throws ClassNotFoundException {
		Class superClass = getType();

		return superClass.isAssignableFrom(subClass);
	}

	/**
	 * check if given signature is the signature of a primitive type
	 * 
	 * @param signature
	 * @return
	 */
	public boolean isPrimitive() {
		return typeSignature.isPrimitiveType(); 
	}

	/**
	 * Returns true if the class represented by superClass is a superclass or
	 * superinterface of the class represented by this type.
	 * 
	 * @param superClassFQN
	 *            the superclass
	 * @return true if superClass is a superclass or superinterface of this type
	 * @throws JavaModelException
	 */
	public boolean isSubOf(Class superClass) throws ClassNotFoundException {
		Class subClass = getType();

		return superClass.isAssignableFrom(subClass);
	}

	/**
	 * get java class corresponding to this type
	 * 
	 * @return the java class corresponding to this type
	 * @throws ClassNotFoundException
	 */
	public Class getType() throws ClassNotFoundException {
		if (javaClass == null) {
			javaClass = getClassFactory().getClass(typeSignature);
		}
		return javaClass;
	}

	public boolean isType(Class clazz) throws ClassNotFoundException {
		return clazz.equals(getType());
	}
	
	/**
	 * check whether this destination type is an array
	 * 
	 * @return
	 */
	public boolean isArray() {
		return typeSignature.isArrayType();
	}

	/**
	 * get the type name (similar to Class.getName())
	 * 
	 * @return the FQN of the type or null if primitive type or array
	 */
	public String getName() {
		return getClassFactory().getName(typeSignature); 
	}
	
	@Override
	public String toString() {
		return typeSignature.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((typeSignature == null) ? 0 : typeSignature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Type other = (Type) obj;
		if (typeSignature == null) {
			if (other.typeSignature != null)
				return false;
		} else if (!typeSignature.equals(other.typeSignature))
			return false;
		return true;
	}

}
