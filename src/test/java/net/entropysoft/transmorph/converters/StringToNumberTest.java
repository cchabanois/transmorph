package net.entropysoft.transmorph.converters;

import java.math.BigDecimal;

import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.ConverterTest;
import junit.framework.TestCase;

public class StringToNumberTest extends TestCase {

	public void testStringToPrimitive() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		// String => int (StringToNumberConverter)
		String myStr = "50";
		int myInt = (Integer) converter.convert(myStr, Integer.TYPE);
		assertEquals(50, myInt);

		try {
			myStr = "129";
			// Value out of range
			byte myByte = (Byte) converter.convert(myStr, Byte.TYPE);
			assertEquals(50, myInt);
			fail("Should not be able to convert");
		} catch (ConverterException e) {

		}
	}

	public void testStringToBigDecimal() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		// String => BigDecimal (StringToNumberConverter)
		BigDecimal bigDecimal = (BigDecimal) converter
				.convert("5.56564546546464646577775612321443244664456",
						BigDecimal.class);
		assertEquals("5.56564546546464646577775612321443244664456", bigDecimal
				.toString());
	}

	public void testStringToNumber() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		Number number = (Number) converter.convert(
				"5.56564546546464646577775612321443244664456", Number.class);
		assertNotNull(number);
		assertTrue(number instanceof Double);
	}

}
