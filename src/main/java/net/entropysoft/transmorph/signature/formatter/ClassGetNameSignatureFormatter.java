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
package net.entropysoft.transmorph.signature.formatter;

import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.signature.ArrayTypeSignature;
import net.entropysoft.transmorph.signature.ClassTypeSignature;
import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeVarSignature;

/**
 * Format a TypeSignature using the same format than Class.getName()
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class ClassGetNameSignatureFormatter implements ITypeSignatureFormatter {

	private static Map<Character, String> primitiveTypesMap = new HashMap<Character, String>();
	private ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter(false);
	
	
	static {
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_BOOLEAN,
				Boolean.TYPE.getName());
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_BYTE, Byte.TYPE
				.getName());
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_CHAR,
				Character.TYPE.getName());
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_DOUBLE,
				Double.TYPE.getName());
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_FLOAT,
				Float.TYPE.getName());
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_INT,
				Integer.TYPE.getName());
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_LONG, Long.TYPE
				.getName());
		primitiveTypesMap.put(PrimitiveTypeSignature.PRIMITIVE_SHORT,
				Short.TYPE.getName());
	}

	public String format(TypeSignature typeSignature) {
		if (typeSignature.isPrimitiveType()) {
			return format((PrimitiveTypeSignature) typeSignature);
		}
		if (typeSignature.isArrayType()) {
			return format((ArrayTypeSignature) typeSignature);
		}
		if (typeSignature.isClassType()) {
			return format((ClassTypeSignature) typeSignature);
		}
		if (typeSignature.isTypeVar()) {
			return format((TypeVarSignature) typeSignature);
		}
		return null;
	}

	private String format(PrimitiveTypeSignature typeSignature) {
		return primitiveTypesMap.get(typeSignature.getPrimitiveTypeChar());
	}

	private String format(ArrayTypeSignature typeSignature) {
		TypeSignature typeErasureSignature = typeSignature.getTypeErasureSignature();
		return typeSignatureFormatter.format(typeErasureSignature);
	}

	private String format(ClassTypeSignature typeSignature) {
		return typeSignature.getClassName();
	}

}
