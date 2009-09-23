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

import java.util.Locale;

import net.entropysoft.transmorph.ConversionContext;

/**
 * Modifier that calls toUpperCase on String
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class UppercaseString implements IModifier<String> {

	private Locale locale;

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public String modify(ConversionContext context, String object) throws ModifierException {
		if (object == null) {
			return null;
		}
		if (locale == null) {
			return object.toUpperCase();
		} else {
			return object.toUpperCase(locale);
		}

	}

}
