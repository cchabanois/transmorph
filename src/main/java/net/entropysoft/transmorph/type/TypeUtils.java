package net.entropysoft.transmorph.type;

public class TypeUtils {

	/**
	 * check if type is a number type (either primitive or instance of Number)
	 * @param type
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public static boolean isNumberType(Type type) throws ClassNotFoundException {
		if (type.isPrimitive()) {
			return ((PrimitiveType)type).isNumber();
		} else {
			return type.isSubOf(Number.class);
		}
	}
	
}
