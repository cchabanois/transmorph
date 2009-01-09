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

import java.util.Collections;
import java.util.Map;

import net.entropysoft.transmorph.cache.LRUMap;
import net.entropysoft.transmorph.signature.ArrayTypeSignature;
import net.entropysoft.transmorph.signature.ClassFactory;
import net.entropysoft.transmorph.signature.ClassTypeSignature;
import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;

/**
 * Factory that creates types from their signatures
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class TypeFactory {
	private Map<TypeSignature, Type> typesCache = Collections
			.synchronizedMap(new LRUMap<TypeSignature, Type>(200));
	private ClassFactory classFactory;

	public TypeFactory(ClassLoader classLoader) {
		this(new ClassFactory(classLoader));
	}

	public TypeFactory(ClassFactory classFactory) {
		this.classFactory = classFactory;
	}

	public ClassFactory getClassFactory() {
		return classFactory;
	}

	public Type getType(Class clazz) {
		return getType(TypeSignatureFactory.getTypeSignature(clazz));
	}

	public Type getType(Class clazz, Class[] typeArgs) {
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(
				clazz, typeArgs);
		return getType(typeSignature);
	}

	public Type getType(TypeSignature typeSignature) {
		Type type = typesCache.get(typeSignature);

		if (type == null) {
			if (typeSignature.isArrayType()) {
				type = new ArrayType(this, (ArrayTypeSignature) typeSignature);
			} else if (typeSignature.isPrimitiveType()) {
				type = new PrimitiveType(this,
						(PrimitiveTypeSignature) typeSignature);
			} else if (typeSignature.isClassType()) {
				type = new ClassType(this, (ClassTypeSignature) typeSignature);
			} else {
				type = null;
			}
			typesCache.put(typeSignature, type);
		}
		return type;
	}

	public ClassType getObjectType() {
		return (ClassType) getType(TypeSignatureFactory
				.getTypeSignature(Object.class));
	}

	public ClassType getStringType() {
		return (ClassType) getType(TypeSignatureFactory
				.getTypeSignature(String.class));
	}

}
