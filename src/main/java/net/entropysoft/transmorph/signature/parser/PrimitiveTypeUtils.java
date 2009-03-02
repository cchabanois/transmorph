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
