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

/**
 * Signature for an array type
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class ArrayTypeSignature extends FieldTypeSignature {

	private TypeSignature componentTypeSignature;
	private TypeSignature typeErasureSignature;

	public ArrayTypeSignature(TypeSignature componentTypeSignature) {
		this.componentTypeSignature = componentTypeSignature;
	}

	/**
	 * get the component TypeSignature
	 * 
	 * @return
	 */
	public TypeSignature getComponentTypeSignature() {
		return componentTypeSignature;
	}

	/**
	 * get the element typeSignature
	 * 
	 * @return
	 */
	public TypeSignature getElementTypeSignature() {
		if (getComponentTypeSignature().isArrayType()) {
			return ((ArrayTypeSignature) getComponentTypeSignature())
					.getElementTypeSignature();
		} else {
			return getComponentTypeSignature();
		}
	}

	/**
	 * get the number of dimensions
	 * 
	 * @return
	 */
	public int getNumDimensions() {
		if (getComponentTypeSignature().isArrayType()) {
			return ((ArrayTypeSignature) getComponentTypeSignature())
					.getNumDimensions() + 1;
		} else {
			return 1;
		}
	}

	public boolean isArrayType() {
		return true;
	}

	@Override
	public String toString() {
		return getSignature();
	}

	@Override
	public TypeSignature getTypeErasureSignature() {
		if (typeErasureSignature == null) {
			typeErasureSignature = new ArrayTypeSignature(
					componentTypeSignature.getTypeErasureSignature());
		}
		return typeErasureSignature;
	}

}
