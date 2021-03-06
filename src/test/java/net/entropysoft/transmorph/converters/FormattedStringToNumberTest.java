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
package net.entropysoft.transmorph.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.NumberFormat;
import java.util.Locale;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class FormattedStringToNumberTest {

	@Test
	public void testFormattedStringToNumber() throws Exception {
		FormattedStringToNumber formattedStringToNumberConverter = new FormattedStringToNumber();
		NumberFormat numberFormat = NumberFormat
				.getNumberInstance(Locale.FRENCH);
		formattedStringToNumberConverter.setNumberFormat(numberFormat);

		Transmorph converter = new Transmorph(formattedStringToNumberConverter);

		float result = converter.convert("-22,33", Float.TYPE);
		assertNotNull(result);
		assertEquals(-22.33, result, 0.001);

		try {
			result = converter.convert("-22,33A", Float.TYPE);
			fail("Should not convert");
		} catch (ConverterException e) {

		}

		try {
			result = converter.convert(null, Float.TYPE);
			fail("Should not convert");
		} catch (ConverterException e) {

		}

		assertNull(converter.convert(null, Float.class));
	}

}
