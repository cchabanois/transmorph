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

import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.FullTypeSignature;

/**
 * Parser for signatures as returned by Class.getName()
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ClassGetNameTypeSignatureParser implements ITypeSignatureParser {

	private CharacterBuffer typeSignature;
	private ClassFileTypeSignatureParser typeSignatureParser;

	public ClassGetNameTypeSignatureParser() {
		typeSignatureParser = new ClassFileTypeSignatureParser();
		typeSignatureParser.setAcceptGenerics(false);
		typeSignatureParser.setUseInternalFormFullyQualifiedName(false);
	}

	public ClassGetNameTypeSignatureParser(String signature) {
		this();
		setTypeSignature(signature);
	}

	public FullTypeSignature parseTypeSignature() {
		Character primitiveChar = PrimitiveTypeUtils.getChar(typeSignature.toString());
		if (primitiveChar != null) {
			return new PrimitiveTypeSignature(primitiveChar);
		}
		typeSignatureParser.setTypeSignature(typeSignature);
		if (typeSignature.peekChar() == '[') {
			FullTypeSignature typeSignature = typeSignatureParser.parseArrayTypeSignature();
			nextChar(CharacterBuffer.EOS);
			return typeSignature;
		} else {
			FullTypeSignature typeSignature = typeSignatureParser.parseClassName();
			nextChar(CharacterBuffer.EOS);
			return typeSignature;
		}
	}

	public void setTypeSignature(String signature) {
		this.typeSignature = new CharacterBuffer(signature);
	}

	private int nextChar(int c) {
		try {
			return typeSignature.nextChar(c);
		} catch (UnexpectedCharacterException e) {
			throw new InvalidSignatureException(e.getMessage(), e.getPosition());
		}
	}	
	
}
