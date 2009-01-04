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

	private ClassFactory classFactory;

	private ClassType objectType;
	private ClassType stringType;
	
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
	
	public Type getType(TypeSignature typeSignature) {
		if (typeSignature.isArrayType()) {
			return new ArrayType(this, (ArrayTypeSignature) typeSignature);
		}
		if (typeSignature.isPrimitiveType()) {
			return new PrimitiveType(this,
					(PrimitiveTypeSignature) typeSignature);
		}
		if (typeSignature.isClassType()) {
			return new ClassType(this, (ClassTypeSignature) typeSignature);
		}
		return null;
	}

	public ClassType getObjectType() {
		if (objectType == null) {
			objectType = new ClassType(this,
					(ClassTypeSignature) TypeSignatureFactory
							.getTypeSignature(Object.class));
		}
		return objectType;
	}

	public ClassType getStringType() {
		if (stringType == null) {
			stringType = new ClassType(this,
					(ClassTypeSignature) TypeSignatureFactory
							.getTypeSignature(String.class));
		}
		return stringType;
	}
	
	
}
