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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class EnumToEnumTest {

	private enum Enum1 {
		FIRST, SECOND, THIRD
	}

	private enum Enum2 {
		FIRST, SECOND, THIRD, FOURTH
	}

	private enum Enum3 {
		ONE, TWO, THREE
	}

	@Test
	public void testEnumToEnumNoMapping() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

		Enum1 enum1 = Enum1.SECOND;

		Enum2 enum2 = converter.convert(enum1, Enum2.class);
		assertEquals(enum2, Enum2.SECOND);

		enum2 = Enum2.FOURTH;

		try {
			enum1 = converter.convert(enum2, Enum1.class);
			fail("Should not convert");
		} catch (ConverterException e) {

		}
	}

	@Test
	public void testEnumToEnumWithMapping() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		EnumToEnum enumToEnum = defaultConverters.getEnumToEnum();
		enumToEnum.addEnumToEnum(Enum2.FIRST, Enum3.ONE);
		enumToEnum.addEnumToEnum(Enum2.SECOND, Enum3.TWO);
		enumToEnum.addEnumToEnum(Enum2.THIRD, Enum3.THREE);
		enumToEnum.addEnumToEnum(Enum2.FOURTH, Enum3.THREE);

		Transmorph converter = new Transmorph(defaultConverters);
		Enum3[] arrayOfEnum3 = converter.convert(new Enum2[] { Enum2.FIRST,
				Enum2.SECOND, Enum2.THIRD, Enum2.FOURTH }, Enum3[].class);
		assertEquals(Enum3.ONE, arrayOfEnum3[0]);
		assertEquals(Enum3.TWO, arrayOfEnum3[1]);
		assertEquals(Enum3.THREE, arrayOfEnum3[2]);
		assertEquals(Enum3.THREE, arrayOfEnum3[3]);
	}

	@Test
	public void testEnumToNull() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		EnumToEnum enumToEnum = defaultConverters.getEnumToEnum();
		enumToEnum.addEnumToEnum(Enum2.FIRST, Enum3.ONE);
		enumToEnum.addEnumToEnum(Enum2.SECOND, Enum3.TWO);
		enumToEnum.addEnumToEnum(Enum2.THIRD, Enum3.THREE);
		enumToEnum.addEnumToNull(Enum2.FOURTH, Enum3.class);

		Transmorph converter = new Transmorph(defaultConverters);
		Enum3[] arrayOfEnum3 = converter.convert(new Enum2[] {
				Enum2.FIRST, Enum2.SECOND, Enum2.THIRD, Enum2.FOURTH },
				Enum3[].class);
		assertEquals(Enum3.ONE, arrayOfEnum3[0]);
		assertEquals(Enum3.TWO, arrayOfEnum3[1]);
		assertEquals(Enum3.THREE, arrayOfEnum3[2]);
		assertEquals(null, arrayOfEnum3[3]);

	}

}
