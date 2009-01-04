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
 * Signature for a primitive type
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class PrimitiveTypeSignature extends TypeSignature {

	public static final char PRIMITIVE_BOOLEAN = 'Z';
	public static final char PRIMITIVE_BYTE = 'B';
	public static final char PRIMITIVE_CHAR = 'C';
	public static final char PRIMITIVE_DOUBLE = 'D';
	public static final char PRIMITIVE_FLOAT = 'F';
	public static final char PRIMITIVE_INT = 'I';
	public static final char PRIMITIVE_LONG = 'J';
	public static final char PRIMITIVE_SHORT = 'S';

	private char primitiveChar;
	private String signature;

	public PrimitiveTypeSignature(char primitiveChar) {
		this.primitiveChar = primitiveChar;
	}

	/**
	 * get the character corresponding to this primitive type
	 * 
	 * @return
	 */
	public char getPrimitiveTypeChar() {
		return primitiveChar;
	}

	public String getSignature() {
		if (signature == null) {
			signature = new String(new char[] { primitiveChar });
		}
		return signature;
	}

	@Override
	public String toString() {
		return getSignature();
	}

	public boolean isPrimitiveType() {
		return true;
	}

	public boolean isBoolean() {
		return primitiveChar == PRIMITIVE_BOOLEAN;
	}

	public boolean isByte() {
		return primitiveChar == PRIMITIVE_BYTE;
	}

	public boolean isChar() {
		return primitiveChar == PRIMITIVE_CHAR;
	}

	public boolean isDouble() {
		return primitiveChar == PRIMITIVE_DOUBLE;
	}

	public boolean isFloat() {
		return primitiveChar == PRIMITIVE_FLOAT;
	}

	public boolean isInt() {
		return primitiveChar == PRIMITIVE_INT;
	}

	public boolean isLong() {
		return primitiveChar == PRIMITIVE_LONG;
	}

	public boolean isShort() {
		return primitiveChar == PRIMITIVE_SHORT;
	}

	public boolean isNumber() {
		return primitiveChar == PRIMITIVE_DOUBLE
				|| primitiveChar == PRIMITIVE_FLOAT
				|| primitiveChar == PRIMITIVE_INT
				|| primitiveChar == PRIMITIVE_LONG
				|| primitiveChar == PRIMITIVE_SHORT
				|| primitiveChar == PRIMITIVE_BYTE;
	}

	@Override
	public TypeSignature getTypeErasureSignature() {
		return this;
	}

}
