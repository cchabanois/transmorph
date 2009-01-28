package net.entropysoft.transmorph.converters;

import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.ConverterTest;
import junit.framework.TestCase;

public class ArrayToArrayTest extends TestCase {

	public void testObjectArrayToStringArray() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		// Object[] => String[] (ArrayToArrayConverter)
		Object[] arrayOfObjects = new Object[] { "one", "two", "three" };
		String[] arrayOfStrings = (String[]) converter.convert(arrayOfObjects,
				(new String[0]).getClass());
		assertEquals("one", arrayOfStrings[0]);
		assertEquals("two", arrayOfStrings[1]);
		assertEquals("three", arrayOfStrings[2]);
	}
	
	public void testMultidimentionalArray() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		
		// int[][] => String[][] (ArrayToArrayConverter)
		int[][] arrayOfArrayOfInts = new int[][] { { 11, 12, 13 },
				{ 21, 22, 23 }, { 31 } };
		String[][] arrayOfArrayOfStrings = (String[][]) converter.convert(
				arrayOfArrayOfInts, (new String[0][0]).getClass());
		for (int i = 0; i < arrayOfArrayOfInts.length; i++) {
			for (int j = 0; j < arrayOfArrayOfInts[i].length; j++) {
				assertEquals(Integer.toString(arrayOfArrayOfInts[i][j]),
						arrayOfArrayOfStrings[i][j]);
			}
		}

		// int[] => String[][] (ArrayToArrayConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		try {
			arrayOfArrayOfStrings = (String[][]) converter.convert(arrayOfInts,
					(new String[0][0]).getClass());
			fail("Should not have been able to convert");
		} catch (ConverterException e) {

		}
	}	
	
}
