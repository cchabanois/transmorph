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

/**
 * Provides tokens to {@link JavaTypeSignatureParser}
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class JavaTypeSignatureLexer {
	private CharacterBuffer characterBuffer;
	private Token[] tokens;
	private int position = 0;

	public enum TokenType {
		// '[]'
		ARRAY,
		// 'java', 'long' ...
		JAVA_ID,
		// '$'
		INNER_CLASS_PREFIX,
		// '<'
		TYPE_ARG_BEGIN,
		// '>'
		TYPE_ARG_END,
		// ','
		TYPE_ARG_SEPARATOR,
		// '?'
		TYPE_ARG_QUESTION_MARK,
		// '.'
		PACKAGE_SEPARATOR, END_OF_TOKENS
	}

	public class Token {
		TokenType tokenType;
		String text;
		int tokenStart;
		int tokenEnd;
	}

	public JavaTypeSignatureLexer(String str) {
		this.characterBuffer = new CharacterBuffer(str);
	}

	public Token nextToken() {
		if (tokens == null) {
			readTokens();
		}
		if (position >= tokens.length) {
			return tokens[tokens.length - 1];
		} else {
			return tokens[position++];
		}
	}

	public Token nextToken(TokenType expectedTokenType) {
		Token token = nextToken();
		if (token.tokenType != expectedTokenType) {
			throw new InvalidSignatureException("Unexpected token at position "
					+ token.tokenStart, token.tokenStart);
		}
		return token;
	}

	public Token peekToken(int i) {
		if (tokens == null) {
			readTokens();
		}
		if (position + i >= tokens.length) {
			return tokens[tokens.length - 1];
		} else {
			return tokens[position + i];
		}
	}

	private void readTokens() {
		List<Token> tokens = new ArrayList<Token>();
		Token token;
		do {
			token = readToken();
			tokens.add(token);
		} while (token.tokenType != TokenType.END_OF_TOKENS);
		this.tokens = tokens.toArray(new Token[tokens.size()]);
	}

	private Token readToken() {
		Token token = new Token();
		try {
			int nextChar = characterBuffer.peekNextNonWhiteSpaceChar();
			token.tokenStart = characterBuffer.getPosition();

			if (nextChar == '$') {
				characterBuffer.nextChar();
				token.tokenType = TokenType.INNER_CLASS_PREFIX;
			} else if (nextChar == '[') {
				characterBuffer.nextChar();
				characterBuffer.nextNonWhiteSpaceChar(']');
				token.tokenType = TokenType.ARRAY;
			} else if (nextChar == '<') {
				characterBuffer.nextChar();
				token.tokenType = TokenType.TYPE_ARG_BEGIN;
			} else if (nextChar == '>') {
				characterBuffer.nextChar();
				token.tokenType = TokenType.TYPE_ARG_END;
			} else if (nextChar == '.') {
				characterBuffer.nextChar();
				token.tokenType = TokenType.PACKAGE_SEPARATOR;
			} else if (nextChar == ',') {
				characterBuffer.nextChar();
				token.tokenType = TokenType.TYPE_ARG_SEPARATOR;
			} else if (nextChar == '?') {
				characterBuffer.nextChar();
				token.tokenType = TokenType.TYPE_ARG_QUESTION_MARK;
			} else if (nextChar == CharacterBuffer.EOS) {
				token.tokenType = TokenType.END_OF_TOKENS;
			} else {
				String javaId = parseJavaId();
				if (javaId != null) {
					token.text = javaId;
					token.tokenType = TokenType.JAVA_ID;
				} else {
					characterBuffer.unexpectedCharacterError();
				}
			}
			token.tokenEnd = characterBuffer.getPosition();
			return token;
		} catch (UnexpectedCharacterException e) {
			throw new InvalidSignatureException(e.getMessage(), e.getPosition());
		}
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
			ch = characterBuffer.peekChar();
			if (ch == characterBuffer.EOS || !isJavaIdentifierPart(ch)) {
				if (sb.length() == 0) {
					return null;
				}
				return sb.toString();
			}
			sb.append((char) ch);
			characterBuffer.nextChar();
		}
	}

	private boolean isJavaIdentifierPart(int codePoint) {
		// if '$' is used as innerClassPrefix, this is not a valid java
		// identifier part
		if (codePoint == '$') {
			return false;
		}
		return Character.isJavaIdentifierPart(codePoint);
	}

	@Override
	public String toString() {
		return characterBuffer.toString();
	}

}
