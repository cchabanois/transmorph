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
 * Modifier that replace null object by another given object
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 * @param <S>
 */
public class ReplaceNull<S> implements IModifier<S> {

	private S replacementObject;

	public ReplaceNull(S replacementObject) {
		this.replacementObject = replacementObject;
	}

	public S modify(ConversionContext context, S object)
			throws ModifierException {
		return object == null ? this.replacementObject : object;
	}

}
