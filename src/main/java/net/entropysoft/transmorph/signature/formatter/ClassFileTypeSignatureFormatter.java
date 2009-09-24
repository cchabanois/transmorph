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
import java.util.List;

import net.entropysoft.transmorph.signature.ArrayTypeSignature;
import net.entropysoft.transmorph.signature.ClassTypeSignature;
import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.TypeArgSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeVarSignature;

/**
 * Format a type signature using internal java type descriptor format
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ClassFileTypeSignatureFormatter implements ITypeSignatureFormatter {

	private char packageSeparator = '/';
	private char innerClassPrefix = '.';

	public ClassFileTypeSignatureFormatter() {

	}

	public ClassFileTypeSignatureFormatter(
			boolean useInternalFormFullyQualifiedName) {
		setUseInternalFormFullyQualifiedName(useInternalFormFullyQualifiedName);
	}

	/**
	 * By default we use the Internal Form of Fully Qualified Name where
	 * identifiers are separated by '/' but you can use the familiar form where
	 * '.' separates the identifiers
	 * 
	 * @param packageSeparator
	 */
	public void setUseInternalFormFullyQualifiedName(boolean value) {
		if (value) {
			packageSeparator = '/';
			innerClassPrefix = '.';
		} else {
			packageSeparator = '.';
			innerClassPrefix = '$';
		}
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
		if (typeSignature.isTypeVar()) {
			return formatTypeVarSignature((TypeVarSignature) typeSignature);
		}
		if (typeSignature.isTypeArgument()) {
			return formatTypeArgSignature((TypeArgSignature) typeSignature);
		}
		return null;
	}

	private String formatArrayTypeSignature(ArrayTypeSignature typeSignature) {
		return '[' + format(typeSignature.getComponentTypeSignature());
	}

	private String formatPrimitiveTypeSignature(
			PrimitiveTypeSignature primitiveTypeSignature) {
		return new String(new char[] { primitiveTypeSignature
				.getPrimitiveTypeChar() });
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
		sb.append('L');
		for (int i = 0; i < classTypeSignatures.length; i++) {
			ClassTypeSignature classTypeSignature = classTypeSignatures[i];
			if (i == 0) {
				sb.append(classTypeSignature.getBinaryName().replace('.',
						packageSeparator));
			} else {
				sb.append(innerClassPrefix);
				sb.append(classTypeSignature.getBinaryName());
			}
			if (classTypeSignature.getTypeArgSignatures().length > 0) {
				sb.append('<');
				for (TypeArgSignature typeArgSignature : classTypeSignature
						.getTypeArgSignatures()) {
					sb.append(formatTypeArgSignature(typeArgSignature));
				}
				sb.append('>');
			}
		}
		sb.append(';');
		return sb.toString();
	}

	public String formatTypeVarSignature(TypeVarSignature typeSignature) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append('T');
		stringBuilder.append(typeSignature.getId());
		stringBuilder.append(';');
		return stringBuilder.toString();
	}

	public String formatTypeArgSignature(TypeArgSignature typeArgSignature) {
		StringBuilder sb = new StringBuilder();
		char wildcard = typeArgSignature.getWildcard();
		if (wildcard != TypeArgSignature.NO_WILDCARD) {
			sb.append(wildcard);
		}
		if (typeArgSignature.getFieldTypeSignature() != null) {
			sb.append(format(typeArgSignature.getFieldTypeSignature()));
		}
		return sb.toString();
	}

}
