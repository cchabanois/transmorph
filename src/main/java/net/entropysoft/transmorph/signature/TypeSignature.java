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

import net.entropysoft.transmorph.signature.formatter.ClassFileTypeSignatureFormatter;

/**
 * Signature for type (primitive, array or class)
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public abstract class TypeSignature implements ISignature {

	private String signature;
	
	public boolean isPrimitiveType() {
		return false;
	}

	public boolean isArrayType() {
		return false;
	}

	public boolean isClassType() {
		return false;
	}
	
	public boolean isTypeVar() {
		return false;
	}
	
	public abstract TypeSignature getTypeErasureSignature();
	
	public String getSignature() {
		if (signature == null) {
			ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();
			signature = typeSignatureFormatter.format(this);
		}
		return signature;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getSignature().hashCode();
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
		TypeSignature other = (TypeSignature) obj;
		if (!getSignature().equals(other.getSignature()))
			return false;
		return true;
	}
	
}
