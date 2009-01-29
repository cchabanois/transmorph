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
package net.entropysoft.transmorph.converters.enums;

import java.lang.annotation.RetentionPolicy;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.converters.TestConverters;

public class StringToEnumTest extends TestCase {

	public void testStringToEnum() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		RetentionPolicy retentionPolicy = (RetentionPolicy) converter.convert(
				"CLASS", RetentionPolicy.class);
		assertEquals(RetentionPolicy.CLASS, retentionPolicy);

		try {
			retentionPolicy = (RetentionPolicy) converter.convert("class",
					RetentionPolicy.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}
	
	
}
