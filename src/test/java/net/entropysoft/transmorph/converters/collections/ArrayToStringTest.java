package net.entropysoft.transmorph.converters.collections;

import static org.junit.Assert.assertEquals;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.ObjectToString;

import org.junit.Test;

public class ArrayToStringTest {

	@Test
	public void testObjectArrayToString() throws Exception {
		Transmorph converter = new Transmorph(new ArrayToString(), new ObjectToString());

		// Object[] => String (ArrayToArray)
		Object[] arrayOfObjects = new Object[] { "one", "two", "three" };
		String str = converter.convert(arrayOfObjects, String.class);
		assertEquals("[one, two, three]", str);
	}

	@Test
	public void testMultidimentionalArrayToString() throws Exception {
		Transmorph converter = new Transmorph(new ArrayToString(), new ObjectToString());

		// int[][] => String (ArrayToString)
		int[][] arrayOfArrayOfInts = new int[][] { { 11, 12, 13 },
				{ 21, 22, 23 }, { 31 } };
		String str = converter.convert(arrayOfArrayOfInts,
				String.class);
		assertEquals("[[11, 12, 13], [21, 22, 23], [31]]", str);
	}

}
