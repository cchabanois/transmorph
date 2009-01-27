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
package net.entropysoft.transmorph.modifiers;

import net.entropysoft.transmorph.ConversionContext;

/**
 * You can add modifiers to most converters. Once object has been converted,
 * modifiers are called on the resulting object (even if this object is null, so
 * you must handle this case)
 * 
 * @author cedric
 * 
 * @param <S>
 */
public interface IModifier<S> {

	/**
	 * Modify and return given object. It can also be used to verify some things
	 * on the object and throw a ModifierException if necessary
	 * @param context
	 * @param object
	 * 
	 * @return
	 * @throws ModifierException
	 */
	public S modify(ConversionContext context, S object) throws ModifierException;

}
