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
package net.entropysoft.transmorph.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Set of known immutable classes
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ImmutableClasses {

	private static final Set<Class<?>> knownImmutables = new HashSet<Class<?>>(
			Arrays.asList(String.class, Byte.class, Short.class, Integer.class,
					Long.class, Float.class, Double.class, Boolean.class,
					BigInteger.class, BigDecimal.class, Character.class));

	public static synchronized void addImmutableClass(Class<?> clazz) {
		knownImmutables.add(clazz);
	}

	public static synchronized boolean isKnownImmutableClass(Class<?> clazz) {
		return knownImmutables.contains(clazz);
	}

}
