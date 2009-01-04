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

import java.util.ArrayList;
import java.util.List;

/**
 * signature for a class type
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ClassTypeSignature extends FieldTypeSignature {

	private String binaryName;
	private TypeArgSignature[] typeArgSignatures;
	private ClassTypeSignature ownerTypeSignature;
	private String signature;
	private TypeSignature typeErasureSignature;

	/**
	 * Creates a Class type signature
	 * 
	 * @param binaryName
	 *            the fqn of the class if not an inner class or the simple name
	 *            if it is an inner class
	 * @param typeArgSignatures
	 *            the type arg signatures or an empty array of not a
	 *            parameterized class type
	 * @param ownerTypeSignature
	 *            the owner type signature if class is an inner class or null if
	 *            not
	 */
	public ClassTypeSignature(String binaryName,
			TypeArgSignature[] typeArgSignatures,
			ClassTypeSignature ownerTypeSignature) {
		this.binaryName = binaryName;
		this.typeArgSignatures = typeArgSignatures;
		this.ownerTypeSignature = ownerTypeSignature;
	}

	/**
	 * Returns true if is an inner class signature
	 * 
	 * @return
	 */
	public boolean isInnerClass() {
		return ownerTypeSignature != null;
	}

	/**
	 * the fqn of the class if not an inner class or the simple name if it is an
	 * inner class
	 * 
	 * @return
	 */
	public String getBinaryName() {
		return binaryName;
	}

	/**
	 * get the fully qualified class name ('$' is used to separate inner
	 * classes)
	 * 
	 * @return
	 */
	public String getClassName() {
		if (ownerTypeSignature == null) {
			return binaryName;
		}
		return ownerTypeSignature.getClassName() + '$' + binaryName;
	}

	public String getInnerClassName() {
		if (ownerTypeSignature == null) {
			return null;
		}
		return binaryName;
	}

	/**
	 * get the owner type signature
	 * 
	 * @return the owner type signature or null if not an inner class
	 */
	public ClassTypeSignature getOwnerTypeSignature() {
		return ownerTypeSignature;
	}

	/**
	 * get the type arguments signatures
	 * 
	 * @return the type arguments signatures or an empty array if not
	 *         parameterized
	 */
	public TypeArgSignature[] getTypeArgSignatures() {
		return typeArgSignatures;
	}

	/**
	 * get the type erasure signature
	 */
	public TypeSignature getTypeErasureSignature() {
		if (typeErasureSignature == null) {
			typeErasureSignature = new ClassTypeSignature(binaryName,
					new TypeArgSignature[0], ownerTypeSignature == null ? null
							: (ClassTypeSignature) ownerTypeSignature
									.getTypeErasureSignature());
		}
		return typeErasureSignature;
	}

	private ClassTypeSignature[] getClassTypeSignatures() {
		List<ClassTypeSignature> list = new ArrayList<ClassTypeSignature>();

		ClassTypeSignature classTypeSignature = this;
		while (classTypeSignature != null) {
			list.add(0, classTypeSignature);
			classTypeSignature = classTypeSignature.ownerTypeSignature;
		}
		return list.toArray(new ClassTypeSignature[list.size()]);
	}

	/**
	 * get the signature as a string
	 */
	public String getSignature() {
		if (signature == null) {
			ClassTypeSignature[] classTypeSignatures = getClassTypeSignatures();

			StringBuilder sb = new StringBuilder();
			sb.append('L');
			for (int i = 0; i < classTypeSignatures.length; i++) {
				ClassTypeSignature classTypeSignature = classTypeSignatures[i];
				if (i == 0) {
					sb.append(classTypeSignature.binaryName.replace('.', '/'));
				} else {
					sb.append('.');
					sb.append(classTypeSignature.binaryName);
				}
				if (classTypeSignature.typeArgSignatures.length > 0) {
					sb.append('<');
					for (TypeArgSignature typeArgSignature : classTypeSignature.typeArgSignatures) {
						sb.append(typeArgSignature.getSignature());
					}
					sb.append('>');
				}
			}
			sb.append(';');
			signature = sb.toString();
		}
		return signature;
	}

	@Override
	public String toString() {
		return getSignature();
	}

	public boolean isClassType() {
		return true;
	}

}
