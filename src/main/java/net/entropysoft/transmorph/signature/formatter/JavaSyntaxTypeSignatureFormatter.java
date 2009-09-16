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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.entropysoft.transmorph.signature.ArrayTypeSignature;
import net.entropysoft.transmorph.signature.ClassTypeSignature;
import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.TypeArgSignature;
import net.entropysoft.transmorph.signature.TypeSignature;

/**
 * Format a type signature using java syntax
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class JavaSyntaxTypeSignatureFormatter implements
		ITypeSignatureFormatter {

	private static Map<Character, String> primitiveTypesMap = new HashMap<Character, String>();

	private boolean useSimpleNames = false;

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

	public boolean isUseSimpleNames() {
		return useSimpleNames;
	}

	public void setUseSimpleNames(boolean useSimpleNames) {
		this.useSimpleNames = useSimpleNames;
	}

	public String format(TypeSignature typeSignature) {
		if (typeSignature.isPrimitiveType()) {
			return formatPrimitiveTypeSignature((PrimitiveTypeSignature) typeSignature);
		}
		if (typeSignature.isArrayType()) {
			return formatArrayTypeSignature((ArrayTypeSignature) typeSignature);
		}
		if (typeSignature.isClassType()) {
			return formatClassTypeSignature((ClassTypeSignature) typeSignature);
		}
		return null;
	}

	private String formatArrayTypeSignature(ArrayTypeSignature typeSignature) {
		return format(typeSignature.getComponentTypeSignature()) + "[]";
	}

	private String formatPrimitiveTypeSignature(
			PrimitiveTypeSignature typeSignature) {
		return primitiveTypesMap.get(typeSignature.getPrimitiveTypeChar());
	}

	private ClassTypeSignature[] getClassTypeSignatures(
			ClassTypeSignature classTypeSignature) {
		List<ClassTypeSignature> list = new ArrayList<ClassTypeSignature>();

		while (classTypeSignature != null) {
			list.add(0, classTypeSignature);
			classTypeSignature = classTypeSignature.getOwnerTypeSignature();
		}
		return list.toArray(new ClassTypeSignature[list.size()]);
	}

	private String formatClassTypeSignature(ClassTypeSignature typeSignature) {
		ClassTypeSignature[] classTypeSignatures = getClassTypeSignatures(typeSignature);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < classTypeSignatures.length; i++) {
			ClassTypeSignature classTypeSignature = classTypeSignatures[i];
			if (i == 0) {
				if (useSimpleNames) {
					sb
							.append(getSimpleName(classTypeSignature
									.getBinaryName()));
				} else {
					sb.append(classTypeSignature.getBinaryName());
				}
			} else {
				sb.append('.');
				sb.append(classTypeSignature.getBinaryName());
			}
			if (classTypeSignature.getTypeArgSignatures().length > 0) {
				sb.append('<');
				TypeArgSignature[] typeArgSignatures = classTypeSignature
						.getTypeArgSignatures();
				for (int j = 0; j < typeArgSignatures.length; j++) {
					if (j != 0) {
						sb.append(',');
					}
					sb.append(formatTypeArgSignature(typeArgSignatures[j]));
				}
				sb.append('>');
			}
		}
		return sb.toString();
	}

	private String getSimpleName(String fullyQualifiedName) {
		// strip the package name
		return fullyQualifiedName
				.substring(fullyQualifiedName.lastIndexOf(".") + 1);
	}

	private String formatTypeArgSignature(TypeArgSignature typeArgSignature) {
		StringBuilder sb = new StringBuilder();
		char wildcard = typeArgSignature.getWildcard();
		if (wildcard == TypeArgSignature.LOWERBOUND_WILDCARD) {
			sb.append("? super ");
		} else if (wildcard == TypeArgSignature.UPPERBOUND_WILDCARD) {
			sb.append("? extends ");
		} else if (wildcard == TypeArgSignature.UNBOUNDED_WILDCARD) {
			sb.append("?");
		}

		if (typeArgSignature.getFieldTypeSignature() != null) {
			sb.append(format(typeArgSignature.getFieldTypeSignature()));
		}
		return sb.toString();
	}
}
