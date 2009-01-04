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

/**
 * Represents an array type
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ArrayType extends Type {

	private Type elementType;
	private Type componentType;
	
	public ArrayType(TypeFactory typeFactory, ArrayTypeSignature typeSignature) {
		super(typeFactory, typeSignature);
	}

	public ArrayTypeSignature getArrayTypeSignature() {
		return (ArrayTypeSignature) typeSignature;
	}

	public Type getElementType() {
		if (elementType == null) {
			elementType = typeFactory.getType(getArrayTypeSignature()
				.getElementTypeSignature());
		}
		return elementType;
	}

	public Type getComponentType() {
		if (componentType == null) {
			componentType = typeFactory.getType(getArrayTypeSignature()
				.getComponentTypeSignature());
		}
		return componentType;
	}

	public int getNumDimensions() {
		return getArrayTypeSignature().getNumDimensions();
	}

}
