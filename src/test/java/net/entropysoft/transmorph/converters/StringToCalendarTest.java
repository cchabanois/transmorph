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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.Transmorph;

public class StringToCalendarTest extends TestCase {

	public void testStringToCalendar() throws Exception {
		StringToCalendar stringToCalendarConverter1 = new StringToCalendar();
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		simpleDateFormat1.setLenient(false);
		stringToCalendarConverter1.setDateFormat(simpleDateFormat1);

		StringToCalendar stringToCalendarConverter2 = new StringToCalendar();
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
		simpleDateFormat2.setLenient(false);
		stringToCalendarConverter2.setDateFormat(simpleDateFormat2);

		Transmorph converter = new Transmorph(stringToCalendarConverter1,
				stringToCalendarConverter2);
		Calendar calendar = converter.convert("29/12/2008",
				Calendar.class);
		assertNotNull(calendar);
		assertEquals(2008, calendar.get(Calendar.YEAR));
		assertEquals(11, calendar.get(Calendar.MONTH));
		assertEquals(29, calendar.get(Calendar.DAY_OF_MONTH));

		calendar = converter.convert("2009", Calendar.class);
		assertNotNull(calendar);
		assertEquals(2009, calendar.get(Calendar.YEAR));

		try {
			calendar = converter.convert("200A", Calendar.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}

}
