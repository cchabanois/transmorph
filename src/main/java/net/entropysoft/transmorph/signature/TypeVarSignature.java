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

public class TypeVarSignature extends FieldTypeSignature {

	private String id;
	private String signature;

	public TypeVarSignature(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getSignature();
	}

	public boolean isTypeVar() {
		return true;
	}
	
	public String getSignature() {
		if (signature == null) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append('T');
			stringBuilder.append(id);
			stringBuilder.append(';');
			signature = stringBuilder.toString();
		}
		return signature;
	}

	@Override
	public TypeSignature getTypeErasureSignature() {
		return this;
	}

}
