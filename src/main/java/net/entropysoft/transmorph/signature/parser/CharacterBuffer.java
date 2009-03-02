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

/**
 * helper class for parser
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class CharacterBuffer {
	private char[] buffer;
	private int position = 0;
	public final static int EOS = -1;
	
	public CharacterBuffer(String str) {
		this.buffer = str.toCharArray();
	}

	public int nextChar() {
		if (position >= buffer.length) {
			return EOS;
		}
		return buffer[position++];
	}

	/**
	 * read next char and make sure it is expected char
	 * 
	 * @param expectedChar
	 */
	public int nextChar(int expectedChar) {
		int ch = nextChar();
		if (ch != expectedChar) {
			position--;
			unexpectedCharacterError();
		}
		return ch;
	}

	public int peekNextNonWhiteSpaceChar() {
		int nextChar = peekChar();
		while (Character.isWhitespace(nextChar)) {
			nextChar();
			nextChar = peekChar();
		}
		return nextChar;
	}
	
	public int nextNonWhiteSpaceChar() {
		int nextChar = nextChar();
		while (Character.isWhitespace(nextChar)) {
			nextChar = nextChar();
		}
		return nextChar;
	}	
	
	public int nextNonWhiteSpaceChar(int expectedChar) {
		int ch = nextNonWhiteSpaceChar();
		if (ch != expectedChar) {
			position--;
			unexpectedCharacterError();
		}
		return ch;
	}	
	
	public int peekChar() {
		if (position >= buffer.length) {
			return EOS;
		}
		return buffer[position];
	}
	
	public int getPosition() {
		return position;
	}

	public void unexpectedCharacterError() {
		throw new UnexpectedCharacterException("Unexpected character '"
				+ (char) peekChar() + "' at position " + position, position);
	}

	@Override
	public String toString() {
		return new String(buffer);
	}
	
	
	
}
