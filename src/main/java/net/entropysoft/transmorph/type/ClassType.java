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

import net.entropysoft.transmorph.signature.ClassTypeSignature;
import net.entropysoft.transmorph.signature.TypeArgSignature;

/**
 * Represents a class type (not a primitive nor an array), possibly
 * parameterized
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ClassType extends Type {

	private Type[] typeArguments;

	public ClassType(TypeFactory typeFactory, ClassTypeSignature typeSignature) {
		super(typeFactory, typeSignature);
	}

	public ClassTypeSignature getClassTypeSignature() {
		return (ClassTypeSignature) typeSignature;
	}

	/**
	 * check if this class is an inner class
	 * 
	 * @return
	 */
	public boolean isInnerClass() {
		return getClassTypeSignature().isInnerClass();
	}

	/**
	 * get the type arguments.
	 * 
	 * @return
	 */
	public Type[] getTypeArguments() {
		if (typeArguments == null) {
			TypeArgSignature[] typeArgSignatures = getClassTypeSignature()
					.getTypeArgSignatures();
			typeArguments = new Type[typeArgSignatures.length];
			for (int i = 0; i < typeArgSignatures.length; i++) {
				if (typeArgSignatures[i].getWildcard() == TypeArgSignature.UNBOUNDED_WILDCARD) {
					typeArguments[i] = typeFactory.getObjectType();
				} else {
					typeArguments[i] = typeFactory.getType(typeArgSignatures[i]
							.getFieldTypeSignature());
				}
			}
		}
		return typeArguments;
	}

}
