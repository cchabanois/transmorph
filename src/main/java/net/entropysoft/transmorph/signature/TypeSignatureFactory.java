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
import net.entropysoft.transmorph.signature.parser.ClassFileTypeSignatureParser;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Factory for TypeSignature
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class TypeSignatureFactory {
	private static JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
	private static Map<String, FullTypeSignature> typeSignatureCache = Collections
			.synchronizedMap(new LRUMap<String, FullTypeSignature>(100));

	/**
	 * get TypeSignature given the signature
	 * 
	 * @param typeSignatureString
	 * @return
	 */
	public static FullTypeSignature getTypeSignature(String typeSignatureString) {
		return getTypeSignature(typeSignatureString, true);
	}

	/**
	 * get TypeSignature given the signature
	 * 
	 * @param typeSignature
	 * @param useInternalFormFullyQualifiedName
	 *            if true, fqn in parameterizedTypeSignature must be in the form
	 *            'java/lang/Thread'. If false fqn must be of the form
	 *            'java.lang.Thread'
	 * @return
	 */
	public static FullTypeSignature getTypeSignature(String typeSignatureString,
			boolean useInternalFormFullyQualifiedName) {
		String key;
		if (!useInternalFormFullyQualifiedName) {
			key = typeSignatureString.replace('.', '/')
					.replace('$', '.');
		} else {
			key = typeSignatureString;
		}

		// we always use the internal form as a key for cache
		FullTypeSignature typeSignature = typeSignatureCache
				.get(key);
		if (typeSignature == null) {
			ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
					typeSignatureString);
			typeSignatureParser.setUseInternalFormFullyQualifiedName(useInternalFormFullyQualifiedName);
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

	public static TypeSignature getTypeSignature(TypeReference<?> type) {
		return javaTypeToTypeSignature.getTypeSignature(type);
	}
	
	/**
	 * get the TypeSignature corresponding to given class
	 * 
	 * @param clazz
	 * @return
	 */
	public static FullTypeSignature getTypeSignature(Class<?> type) {
		return (FullTypeSignature)javaTypeToTypeSignature.getTypeSignature(type);
	}
	
	/**
	 * get the TypeSignature corresponding to given class with given type
	 * arguments
	 * 
	 * @param clazz
	 * @param typeArgs
	 * @return
	 */
	public static FullTypeSignature getTypeSignature(Class<?> clazz, Class<?>[] typeArgs) {
		ClassTypeSignature rawClassTypeSignature = (ClassTypeSignature) javaTypeToTypeSignature
				.getTypeSignature(clazz);
		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[typeArgs.length];
		for (int i = 0; i < typeArgs.length; i++) {
			typeArgSignatures[i] = new TypeArgSignature(
					TypeArgSignature.NO_WILDCARD,
					(FieldTypeSignature) javaTypeToTypeSignature
							.getTypeSignature(typeArgs[i]));
		}
		ClassTypeSignature classTypeSignature = new ClassTypeSignature(
				rawClassTypeSignature.getBinaryName(), typeArgSignatures,
				rawClassTypeSignature.getOwnerTypeSignature());

		return classTypeSignature;
	}

}
