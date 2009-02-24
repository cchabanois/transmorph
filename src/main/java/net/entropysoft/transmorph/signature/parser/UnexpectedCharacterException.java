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
 * Exception thrown by CharacterBuffer when there is an unexpected character 
 *
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class UnexpectedCharacterException extends RuntimeException {

	private static final long serialVersionUID = -8254166255685531654L;
	
	private int position;
	
	public UnexpectedCharacterException(String message, int position) {
		super(message);
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

}
