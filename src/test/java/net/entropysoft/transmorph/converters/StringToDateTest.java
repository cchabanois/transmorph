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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class StringToDateTest {

	@Test
	public void testStringToDate() throws Exception {
		// parse date with format dd/MM/yyyy or yyyy
		StringToDate stringToDateConverter1 = new StringToDate();
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		simpleDateFormat1.setLenient(false);
		stringToDateConverter1.setDateFormat(simpleDateFormat1);

		StringToDate stringToDateConverter2 = new StringToDate();
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
		simpleDateFormat2.setLenient(false);
		stringToDateConverter2.setDateFormat(simpleDateFormat2);

		Transmorph converter = new Transmorph(stringToDateConverter1,
				stringToDateConverter2);
		Date date = converter.convert("29/12/2008", Date.class);
		assertNotNull(date);

		date = converter.convert("2009", Date.class);
		assertNotNull(date);

		try {
			date = converter.convert("200A", Date.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}	
	
}
