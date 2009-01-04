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
package net.entropysoft.transmorph.signature;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates class from TypeSignature
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ClassFactory {

	private ClassLoader classLoader;

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

	public ClassFactory(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * get the type name (similar to Class.getName()) without loading the class
	 * 
	 * @return the FQN of the type or null if primitive type or array
	 */
	public String getName(TypeSignature typeSignature) {
		if (typeSignature.isPrimitiveType()) {
			PrimitiveTypeSignature primitiveTypeSignature = (PrimitiveTypeSignature) typeSignature;
			return primitiveTypesMap.get(
					primitiveTypeSignature.getPrimitiveTypeChar()).getName();
		}
		if (typeSignature.isArrayType()) {
			return typeSignature.getTypeErasureSignature().getSignature()
					.replace('/', '.');
		}
		if (typeSignature.isClassType()) {
			ClassTypeSignature classTypeSignature = (ClassTypeSignature) typeSignature;
			return classTypeSignature.getClassName();
		}
		return null;
	}

	/**
	 * return the class corresponding to the type signature or null if type
	 * signature is a TypeVarSignature
	 * 
	 * @param typeSignature
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Class getClass(TypeSignature typeSignature)
			throws ClassNotFoundException {
		if (typeSignature.isPrimitiveType()) {
			PrimitiveTypeSignature primitiveTypeSignature = (PrimitiveTypeSignature) typeSignature;
			return primitiveTypesMap.get(primitiveTypeSignature
					.getPrimitiveTypeChar());
		}
		if (typeSignature.isArrayType()) {
			return Class.forName(getName(typeSignature), true, classLoader);
		}
		if (typeSignature.isClassType()) {
			ClassTypeSignature classTypeSignature = (ClassTypeSignature) typeSignature;
			return Class.forName(classTypeSignature.getClassName(), true,
					classLoader);
		}
		return null;
	}

}
