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
 * Signature for a type argument
 * 
 * This class is thread-safe
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class TypeArgSignature extends TypeSignature {

	public static char NO_WILDCARD = ' '; // <Type>
	public static char UNBOUNDED_WILDCARD = '*'; // <?>
	public static char UPPERBOUND_WILDCARD = '+'; // <N extends Type>
	public static char LOWERBOUND_WILDCARD = '-'; // <N super Type>
	private final char wildcard;
	private final FieldTypeSignature fieldTypeSignature;
	private volatile String signature;

	public TypeArgSignature(char wildcard, FieldTypeSignature fieldTypeSignature) {
		this.wildcard = wildcard;
		this.fieldTypeSignature = fieldTypeSignature;
	}

	public char getWildcard() {
		return wildcard;
	}

	/**
	 * get the signature of the type. Null if unbounded
	 * 
	 * @return
	 */
	public FieldTypeSignature getFieldTypeSignature() {
		return fieldTypeSignature;
	}

	public String getSignature() {
		if (signature == null) {
			ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();
			signature = typeSignatureFormatter.formatTypeArgSignature(this);
		}
		return signature;
	}

	@Override
	public String toString() {
		return getSignature();
	}

	@Override
	public boolean isTypeArgument() {
		return true;
	}

	@Override
	public FullTypeSignature getTypeErasureSignature() {
		if (wildcard == NO_WILDCARD)
			return getFieldTypeSignature().getTypeErasureSignature();
		if (wildcard == UNBOUNDED_WILDCARD)
			return TypeSignatureFactory.getTypeSignature(Object.class);
		if (wildcard == UPPERBOUND_WILDCARD)
			return getFieldTypeSignature().getTypeErasureSignature();
		if (wildcard == LOWERBOUND_WILDCARD)
			return TypeSignatureFactory.getTypeSignature(Object.class);
		return null;
	}

}
