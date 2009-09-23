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

import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;

public class PrimitiveTypeUtils {

	private final static Map<String, Character> nameToPrimitiveChar = new HashMap<String, Character>();	
	
	static {
		nameToPrimitiveChar.put("boolean",
				PrimitiveTypeSignature.PRIMITIVE_BOOLEAN);
		nameToPrimitiveChar.put("char", PrimitiveTypeSignature.PRIMITIVE_CHAR);
		nameToPrimitiveChar.put("byte", PrimitiveTypeSignature.PRIMITIVE_BYTE);
		nameToPrimitiveChar
				.put("short", PrimitiveTypeSignature.PRIMITIVE_SHORT);
		nameToPrimitiveChar.put("int", PrimitiveTypeSignature.PRIMITIVE_INT);
		nameToPrimitiveChar
				.put("float", PrimitiveTypeSignature.PRIMITIVE_FLOAT);
		nameToPrimitiveChar.put("long", PrimitiveTypeSignature.PRIMITIVE_LONG);
		nameToPrimitiveChar.put("double",
				PrimitiveTypeSignature.PRIMITIVE_DOUBLE);
	}	

	public static Character getChar(String primitiveName) {
		return nameToPrimitiveChar.get(primitiveName);
	}
	
	
}
