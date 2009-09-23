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
package net.entropysoft.transmorph.signature.parser;

import java.util.ArrayList;
import java.util.List;

import net.entropysoft.transmorph.signature.ArrayTypeSignature;
import net.entropysoft.transmorph.signature.ClassTypeSignature;
import net.entropysoft.transmorph.signature.FieldTypeSignature;
import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.TypeArgSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeVarSignature;

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
public class ClassFileTypeSignatureParser implements ITypeSignatureParser {
	private static final int EOS = CharacterBuffer.EOS;
	private boolean acceptGenerics = true;
	private char packageSeparator = '/';
	private char innerClassPrefix = '.';
	private CharacterBuffer characterBuffer;

	public ClassFileTypeSignatureParser() {

	}

	public ClassFileTypeSignatureParser(boolean useInternalFormFullyQualifiedName) {
		setUseInternalFormFullyQualifiedName(useInternalFormFullyQualifiedName);
	}

	public ClassFileTypeSignatureParser(String typeSignature) {
		setTypeSignature(typeSignature);
	}

	public ClassFileTypeSignatureParser(String typeSignature,
			boolean useInternalFormFullyQualifiedName) {
		setTypeSignature(typeSignature);
		setUseInternalFormFullyQualifiedName(useInternalFormFullyQualifiedName);
	}

	public void setTypeSignature(String signature) {
		characterBuffer = new CharacterBuffer(signature);
	}

	public void setTypeSignature(CharacterBuffer characterBuffer) {
		this.characterBuffer = characterBuffer;
	}

	public boolean isAcceptGenerics() {
		return acceptGenerics;
	}

	/**
	 * set whether generics are accepted or not. If true, there will be no error
	 * while parsing "java.util.List<java.lang.String>"
	 * 
	 * @param acceptGenerics
	 */
	public void setAcceptGenerics(boolean acceptGenerics) {
		this.acceptGenerics = acceptGenerics;
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
		TypeSignature typeSignature = parseClassFileTypeSignature();
		nextChar(EOS);
		return typeSignature;
	}

	private TypeSignature parseClassFileTypeSignature() {
		int ch = peekChar();
		if (isPrimitiveTypeCharacter(ch)) {
			TypeSignature typeSignature = parsePrimitiveTypeSignature();
			return typeSignature;
		} else {
			TypeSignature typeSignature = parseFieldTypeSignature();
			return typeSignature;
		}
	}
	
	private int peekChar() {
		return characterBuffer.peekChar();
	}

	private FieldTypeSignature parseFieldTypeSignature() {
		int ch = peekChar();
		switch (ch) {
		case 'L':
			return parseClassTypeSignature();
		case '[':
			return parseArrayTypeSignature();
		case 'T':
			if (acceptGenerics)
				return parseTypeVarSignature();
		}
		unexpectedCharacterError();
		return null;
	}

	private void unexpectedCharacterError() {
		try {
			characterBuffer.unexpectedCharacterError();
		} catch (UnexpectedCharacterException e) {
			throw new InvalidSignatureException(e.getMessage(), e.getPosition());
		}
	}

	private TypeVarSignature parseTypeVarSignature() {
		nextChar('T');
		String id = parseJavaId();
		nextChar(';');
		return new TypeVarSignature(id);
	}

	private int nextChar(int c) {
		try {
			return characterBuffer.nextChar(c);
		} catch (UnexpectedCharacterException e) {
			throw new InvalidSignatureException(e.getMessage(), e.getPosition());
		}
	}

	/**
	 * parse primitive type signature "Z | C | B | S | I | F | J | D"
	 * 
	 * @return
	 */
	public PrimitiveTypeSignature parsePrimitiveTypeSignature() {
		int ch = nextChar();
		return new PrimitiveTypeSignature((char) ch);
	}

	private int nextChar() {
		return characterBuffer.nextChar();
	}

	/**
	 * parse an array type signature "[ TypeSignature"
	 * 
	 * @return
	 */
	public ArrayTypeSignature parseArrayTypeSignature() {
		nextChar('[');
		return new ArrayTypeSignature(parseClassFileTypeSignature());
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
			return new TypeArgSignature(
					(char) nextChar(TypeArgSignature.UNBOUNDED_WILDCARD), null);
		case '+':
			return new TypeArgSignature(
					(char) nextChar(TypeArgSignature.UPPERBOUND_WILDCARD),
					parseFieldTypeSignature());
		case '-':
			return new TypeArgSignature(
					(char) nextChar(TypeArgSignature.LOWERBOUND_WILDCARD),
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

	private boolean isJavaIdentifierPart(int codePoint) {
		// if '$' is used as innerClassPrefix, this is not a valid java
		// identifier part
		if (innerClassPrefix == '$' && codePoint == '$') {
			return false;
		}
		return Character.isJavaIdentifierPart(codePoint);
	}

	/**
	 * parse a java id
	 * 
	 * @return
	 */
	private String parseJavaId() {
		StringBuilder sb = new StringBuilder();
		int ch;
		while (true) {
			ch = peekChar();
			if (ch == EOS || !isJavaIdentifierPart(ch)) {
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
		sb.append(parseJavaId());
		while (peekChar() == packageSeparator) { // '/' or '.'
			nextChar(packageSeparator);
			sb.append('.');
			sb.append(parseJavaId());
		}
		return sb.toString();
	}

	private ClassTypeSignature parseInnerClass(
			ClassTypeSignature ownerClassTypeSignature) {
		nextChar(innerClassPrefix); // '.' or '$'
		String id = parseJavaId();
		int ch = peekChar();
		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[0];
		if (acceptGenerics && ch == '<') {
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
		ClassTypeSignature classTypeSignature = parseClassName();
		nextChar(';');
		return classTypeSignature;
	}

	/**
	 * parse the class name (outer class and inner class)
	 * 
	 * @return
	 */
	public ClassTypeSignature parseClassName() {
		// read ID (/Id)*
		String outerClassName = parseOuterClassName();

		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[0];
		int ch = peekChar();
		if (acceptGenerics && ch == '<') {
			typeArgSignatures = parseTypeArgs();
		}
		ClassTypeSignature classTypeSignature = new ClassTypeSignature(
				outerClassName, typeArgSignatures, null);
		ch = peekChar();
		if (ch == innerClassPrefix) { // '.' or '$'
			classTypeSignature = parseInnerClasses(classTypeSignature);
		}
		return classTypeSignature;
	}

}
