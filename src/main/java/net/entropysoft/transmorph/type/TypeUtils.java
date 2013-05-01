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
package net.entropysoft.transmorph.type;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

/**
 * This code mostly comes from gson (http://code.google.com/p/google-gson/)
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class TypeUtils {

	/**
	 * This method returns the actual raw class associated with the specified
	 * type.
	 */
	public static Class<?> getRawType(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			ParameterizedType actualType = (ParameterizedType) type;
			return getRawType(actualType.getRawType());
		} else if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;
			Object rawArrayType = Array.newInstance(getRawType(genericArrayType
					.getGenericComponentType()), 0);
			return rawArrayType.getClass();
		} else if (type instanceof WildcardType) {
			WildcardType castedType = (WildcardType) type;
			return getRawType(castedType.getUpperBounds()[0]);
		} else {
			throw new IllegalArgumentException(
					"Type \'"
							+ type
							+ "\' is not a Class, "
							+ "ParameterizedType, or GenericArrayType. Can't extract class.");
		}
	}
	
	
	/**
	 * Check if this type is assignable from the given Type.
	 */
	public static boolean isAssignableFrom(TypeReference<?> from, TypeReference<?> to) {
		if (from == null) {
			return false;
		}

		if (to.equals(from)) {
			return true;
		}

		if (to.getType() instanceof Class) {
			return to.getRawType().isAssignableFrom(from.getRawType());
		} else if (to.getType() instanceof ParameterizedType) {
			return isAssignableFrom(from.getType(), (ParameterizedType) to
					.getType(), new HashMap<String, Type>());
		} else if (to.getType() instanceof GenericArrayType) {
			return to.getRawType().isAssignableFrom(from.getRawType())
					&& isAssignableFrom(from.getType(), (GenericArrayType) to
							.getType());
		} else {
			throw new AssertionError("Unexpected Type : " + to);
		}
	}

	/**
	 * Private recursive helper function to actually do the type-safe checking
	 * of assignability.
	 */
	private static boolean isAssignableFrom(Type from, ParameterizedType to,
			Map<String, Type> typeVarMap) {

		if (from == null) {
			return false;
		}

		if (to.equals(from)) {
			return true;
		}

		// First figure out the class and any type information.
		Class<?> clazz = getRawType(from);
		ParameterizedType ptype = null;
		if (from instanceof ParameterizedType) {
			ptype = (ParameterizedType) from;
		}

		// Load up parameterized variable info if it was parameterized.
		if (ptype != null) {
			Type[] tArgs = ptype.getActualTypeArguments();
			TypeVariable<?>[] tParams = clazz.getTypeParameters();
			for (int i = 0; i < tArgs.length; i++) {
				Type arg = tArgs[i];
				TypeVariable<?> var = tParams[i];
				while (arg instanceof TypeVariable) {
					TypeVariable<?> v = (TypeVariable<?>) arg;
					arg = typeVarMap.get(v.getName());
				}
				typeVarMap.put(var.getName(), arg);
			}

			// check if they are equivalent under our current mapping.
			if (typeEquals(ptype, to, typeVarMap)) {
				return true;
			}
		}

		for (Type itype : clazz.getGenericInterfaces()) {
			if (isAssignableFrom(itype, to, new HashMap<String, Type>(
					typeVarMap))) {
				return true;
			}
		}

		// Interfaces didn't work, try the superclass.
		Type sType = clazz.getGenericSuperclass();
		if (isAssignableFrom(sType, to, new HashMap<String, Type>(typeVarMap))) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if two parameterized types are exactly equal, under the variable
	 * replacement described in the typeVarMap.
	 */
	private static boolean typeEquals(ParameterizedType from,
			ParameterizedType to, Map<String, Type> typeVarMap) {
		if (from.getRawType().equals(to.getRawType())) {
			Type[] fromArgs = from.getActualTypeArguments();
			Type[] toArgs = to.getActualTypeArguments();
			for (int i = 0; i < fromArgs.length; i++) {
				if (!matches(fromArgs[i], toArgs[i], typeVarMap)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if two types are the same or are equivalent under a variable
	 * mapping given in the type map that was provided.
	 */
	private static boolean matches(Type from, Type to, Map<String, Type> typeMap) {
		if (to.equals(from))
			return true;

		if (from instanceof TypeVariable) {
			return to.equals(typeMap.get(((TypeVariable<?>) from).getName()));
		}

		return false;
	}

	/**
	 * Private helper function that performs some assignability checks for the
	 * provided GenericArrayType.
	 */
	private static boolean isAssignableFrom(Type from, GenericArrayType to) {
		Type toGenericComponentType = to.getGenericComponentType();
		if (toGenericComponentType instanceof ParameterizedType) {
			Type t = from;
			if (from instanceof GenericArrayType) {
				t = ((GenericArrayType) from).getGenericComponentType();
			} else if (from instanceof Class) {
				Class<?> classType = (Class<?>) from;
				while (classType.isArray()) {
					classType = classType.getComponentType();
				}
				t = classType;
			}
			return isAssignableFrom(t,
					(ParameterizedType) toGenericComponentType,
					new HashMap<String, Type>());
		}
		// No generic defined on "to"; therefore, return true and let other
		// checks determine assignability
		return true;
	}

}
