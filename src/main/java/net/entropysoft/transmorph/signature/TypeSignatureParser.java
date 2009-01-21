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
 * Parser for java type signatures
 * 
 * <pre>
 * TypeSignature: Z | C | B | S | I | F | J | D | FieldTypeSignature
 * FieldTypeSignature: ClassTypeSignature | [ TypeSignature | TypeVar
 * ClassTypeSignature: L Id ( / Id )* TypeArgs? ( . Id TypeArgs? )* ;
 * TypeArgs: &lt; TypeArg+ &gt;
 * TypeArg: * | ( + | - )? FieldTypeSignature
 * TypeVar: T Id ;
 * </pre>
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class TypeSignatureParser {
	private char[] signature;
	private int position = 0;
	private static int EOS = -1;

	private char packageSeparator = '/';
	private char innerClassPrefix = '.';

	public TypeSignatureParser(String typeSignature) {
		this.signature = typeSignature.toCharArray();
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

	public char getPackageSeparator() {
		return packageSeparator;
	}

	public void setPackageSeparator(char packageSeparator) {
		this.packageSeparator = packageSeparator;
	}

	public char getInnerClassPrefix() {
		return innerClassPrefix;
	}

	public void setInnerClassPrefix(char innerClassPrefix) {
		this.innerClassPrefix = innerClassPrefix;
	}

	private int nextChar() {
		if (position >= signature.length) {
			return EOS;
		}
		return signature[position++];
	}

	/**
	 * read next char and make sure it is expected char
	 * 
	 * @param expectedChar
	 */
	private int nextChar(int expectedChar) {
		int ch = nextChar();
		if (ch != expectedChar) {
			unexpectedCharacterError();
		}
		return ch;
	}

	private int peekChar() {
		if (position >= signature.length) {
			return EOS;
		}
		return signature[position];
	}

	private boolean isPrimitiveTypeCharacter(int ch) {
		return ch == PrimitiveTypeSignature.PRIMITIVE_BOOLEAN
				|| ch == PrimitiveTypeSignature.PRIMITIVE_BYTE
				|| ch == PrimitiveTypeSignature.PRIMITIVE_CHAR
				|| ch == PrimitiveTypeSignature.PRIMITIVE_DOUBLE
				|| ch == PrimitiveTypeSignature.PRIMITIVE_FLOAT
				|| ch == PrimitiveTypeSignature.PRIMITIVE_INT
				|| ch == PrimitiveTypeSignature.PRIMITIVE_LONG
				|| ch == PrimitiveTypeSignature.PRIMITIVE_SHORT;
	}

	public TypeSignature parseTypeSignature() {
		int ch = peekChar();
		if (isPrimitiveTypeCharacter(ch)) {
			return parsePrimitiveTypeSignature();
		}
		return parseFieldTypeSignature();
	}

	private FieldTypeSignature parseFieldTypeSignature() {
		int ch = peekChar();
		switch (ch) {
		case 'L':
			return parseClassTypeSignature();
		case '[':
			return parseArrayTypeSignature();
		case 'T':
			return parseTypeVarSignature();
		}
		unexpectedCharacterError();
		return null;
	}

	private TypeVarSignature parseTypeVarSignature() {
		nextChar('T');
		String id = parseId();
		nextChar(';');
		return new TypeVarSignature(id);
	}

	private void unexpectedCharacterError() {
		throw new InvalidSignatureException("Unexpected character '"
				+ (char) peekChar() + "' at position " + position, position);
	}

	/**
	 * parse primitive type signature "Z | C | B | S | I | F | J | D"
	 * 
	 * @return
	 */
	private PrimitiveTypeSignature parsePrimitiveTypeSignature() {
		int ch = nextChar();
		return new PrimitiveTypeSignature((char) ch);
	}

	/**
	 * parse an array type signature "[ TypeSignature"
	 * 
	 * @return
	 */
	private ArrayTypeSignature parseArrayTypeSignature() {
		nextChar('[');
		return new ArrayTypeSignature(parseTypeSignature());
	}

	/**
	 * parse a java id
	 * 
	 * @return
	 */
	private String parseId() {
		StringBuilder sb = new StringBuilder();
		int ch;
		while (true) {
			ch = peekChar();
			if (ch == EOS || !Character.isJavaIdentifierPart(ch)) {
				return sb.toString();
			}
			sb.append((char) ch);
			nextChar();
		}
	}

	/**
	 * parse the outer class name
	 * 
	 * @return
	 */
	private String parseOuterClassName() {
		StringBuilder sb = new StringBuilder();
		sb.append(parseId());
		int ch;
		while ((ch = peekChar()) == packageSeparator) { // '/' or '.'
			nextChar(packageSeparator);
			sb.append('.');
			sb.append(parseId());
		}
		return sb.toString();
	}

	/**
	 * "* | ( + | - )? FieldTypeSignature"
	 * 
	 * @return
	 */
	private TypeArgSignature parseTypeArg() {
		int ch = peekChar();
		switch (ch) {
		case '*':
			return new TypeArgSignature((char) nextChar('*'), null);
		case '+':
			return new TypeArgSignature((char) nextChar('+'),
					parseFieldTypeSignature());
		case '-':
			return new TypeArgSignature((char) nextChar('-'),
					parseFieldTypeSignature());
		default:
			return new TypeArgSignature(TypeArgSignature.NO_WILDCARD,
					parseFieldTypeSignature());
		}
	}

	private TypeArgSignature[] parseTypeArgs() {
		nextChar('<');
		List<TypeArgSignature> typeArgSignatures = new ArrayList<TypeArgSignature>();
		typeArgSignatures.add(parseTypeArg());

		int ch = peekChar();
		while (ch != '>' && ch != EOS) {
			typeArgSignatures.add(parseTypeArg());
			ch = peekChar();
		}
		nextChar('>');
		return typeArgSignatures.toArray(new TypeArgSignature[typeArgSignatures
				.size()]);
	}

	private ClassTypeSignature parseInnerClass(
			ClassTypeSignature ownerClassTypeSignature) {
		nextChar(innerClassPrefix); // '.' or '$'
		String id = parseId();
		int ch = peekChar();
		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[0];
		if (ch == '<') {
			typeArgSignatures = parseTypeArgs();
		}
		return new ClassTypeSignature(id, typeArgSignatures,
				ownerClassTypeSignature);
	}

	private ClassTypeSignature parseInnerClasses(
			ClassTypeSignature ownerClassTypeSignature) {
		ClassTypeSignature classTypeSignature = parseInnerClass(ownerClassTypeSignature);
		while (true) {
			int ch = peekChar();
			if (ch != innerClassPrefix) { // '.' or '$'
				return classTypeSignature;
			}
			classTypeSignature = parseInnerClass(classTypeSignature);
		}
	}

	/**
	 * ClassTypeSignature: L Id ( / Id )* TypeArgs? ( . Id TypeArgs? )* ;
	 * 
	 * @return
	 */
	private ClassTypeSignature parseClassTypeSignature() {
		nextChar('L');
		// read ID (/Id)*
		String outerClassName = parseOuterClassName();

		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[0];
		int ch = peekChar();
		if (ch == '<') {
			typeArgSignatures = parseTypeArgs();
		}
		ClassTypeSignature classTypeSignature = new ClassTypeSignature(
				outerClassName, typeArgSignatures, null);
		ch = peekChar();
		if (ch == innerClassPrefix) { // '.' or '$'
			classTypeSignature = parseInnerClasses(classTypeSignature);
		}
		nextChar(';');
		return classTypeSignature;
	}
}
