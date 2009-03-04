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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.collections.ArrayToCollection;
import net.entropysoft.transmorph.modifiers.IModifier;
import net.entropysoft.transmorph.modifiers.UppercaseString;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeFactory;

public class MultiStepConverterTest extends TestCase {

	public void testMultiStepConverter() throws Exception {
		TypeFactory typeFactory = new TypeFactory(MultiStepConverterTest.class
				.getClassLoader());

		MultiStepConverter multiStepConverter = new MultiStepConverter(
				new Type[] { typeFactory.getType(String.class),
						typeFactory.getType(Integer.class),
						typeFactory.getType(Boolean.TYPE) });

		IConverter intToBoolean = new AbstractSimpleConverter(Integer.class,
				Boolean.TYPE) {

			@Override
			public Object doConvert(ConversionContext context,
					Object sourceObject, Type destinationType)
					throws ConverterException {
				int theInt = ((Number) sourceObject).intValue();
				if (theInt == 0) {
					return false;
				} else {
					return true;
				}
			}

		};

		Transmorph converter = new Transmorph(typeFactory, new IConverter[] { new StringToNumber(),
				new NumberToNumber(), intToBoolean, multiStepConverter });
		assertTrue((Boolean) converter.convert("22", Boolean.TYPE));
		assertFalse((Boolean) converter.convert("0", Boolean.TYPE));
	}

	public void testMultiStepWithConverters() throws Exception {
		TypeFactory typeFactory = new TypeFactory(MultiStepConverterTest.class
				.getClassLoader());

		// we will convert an array of Dates to a List of uppercased strings

		// use an ObjectToFormattedString to convert Date to string
		ObjectToFormattedString dateToString = new ObjectToFormattedString(
				Date.class, new SimpleDateFormat("EEE, MMM d, yy", Locale.US));
		dateToString.setModifiers(new IModifier[] { new UppercaseString() });

		ArrayToCollection arrayToCollection = new ArrayToCollection();
		arrayToCollection.setElementConverter(dateToString);

		Transmorph converter = new Transmorph(MultiStepConverterTest.class
				.getClassLoader(), arrayToCollection);

		Date[] dates = new Date[] { new Date(0), new Date(1232621965342L) };
		List<String> listOfstrings = (List<String>) converter.convert(dates,
				List.class, new Class[] { String.class });
		assertEquals("THU, JAN 1, 70", listOfstrings.get(0));
		assertEquals("THU, JAN 22, 09", listOfstrings.get(1));
	}
	
	
}
