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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class ObjectToFormattedStringTest {

	@Test
	public void testObjectToFormattedString() throws Exception {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				Locale.FRANCE);

		ObjectToFormattedString objectToFormattedStringConverter = new ObjectToFormattedString(
				Date.class, df);

		Transmorph converter = new Transmorph(objectToFormattedStringConverter);

		Calendar calendar = Calendar.getInstance(Locale.FRANCE);
		calendar.set(2009, 0, 1, 0, 0, 0);
		String str = converter.convert(calendar.getTime(), String.class);
		assertNotNull(str);
		assertEquals("01/01/09", str);

	}

	@Test
	public void testIndirectObjectToFormattedString() throws Exception {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				Locale.FRANCE);
		// source class is different from formatExpectedClass : we will use the
		// elementConverter to convert
		// from source class to formatExpectedClass
		ObjectToFormattedString objectToFormattedStringConverter = new ObjectToFormattedString(
				Calendar.class, Date.class, df);

		Transmorph converter = new Transmorph(objectToFormattedStringConverter,
				new CalendarToDate());

		Calendar calendar = Calendar.getInstance(Locale.FRANCE);
		calendar.set(2009, 0, 1, 0, 0, 0);
		String str = converter.convert(calendar, String.class);
		assertNotNull(str);
		assertEquals("01/01/09", str);

	}

}
