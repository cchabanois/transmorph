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

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import net.entropysoft.transmorph.cache.LRUMap;

public class TypeSignatureFactory {
	private static JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
	private static Map<String, TypeSignature> typeSignatureCache = Collections.synchronizedMap(new LRUMap<String, TypeSignature>(100));
	
	/**
	 * get TypeSignature given the signature
	 * @param typeSignature
	 * @return
	 */
	public static TypeSignature getTypeSignature(String typeSignatureString) {
		TypeSignature typeSignature = typeSignatureCache.get(typeSignatureString);
		if (typeSignature == null) {
			TypeSignatureParser typeSignatureParser = new TypeSignatureParser(
				typeSignatureString);
			typeSignature = typeSignatureParser.parseTypeSignature();
			typeSignatureCache.put(typeSignatureString, typeSignature);
		}
		return typeSignature;
	}

	/**
	 * get the TypeSignature corresponding to given class
	 * 
	 * @param clazz
	 * @return
	 */
	public static TypeSignature getTypeSignature(Type type) {
		return javaTypeToTypeSignature.getTypeSignature(type);
	}
	
	/**
	 * get the TypeSignature corresponding to given class with given type arguments
	 * @param clazz
	 * @param typeArgs
	 * @return
	 */
	public static TypeSignature getTypeSignature(Class clazz, Class[] typeArgs)	 {
		ClassTypeSignature rawClassTypeSignature = (ClassTypeSignature)javaTypeToTypeSignature.getTypeSignature(clazz);
		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[typeArgs.length];
		for (int i = 0; i < typeArgs.length; i++) {
			typeArgSignatures[i] = new TypeArgSignature(TypeArgSignature.NO_WILDCARD, (FieldTypeSignature)javaTypeToTypeSignature.getTypeSignature(typeArgs[i]));
		}
		ClassTypeSignature classTypeSignature = new ClassTypeSignature(rawClassTypeSignature.getBinaryName(), typeArgSignatures, rawClassTypeSignature.getOwnerTypeSignature());

		return classTypeSignature;
	}
	
}
