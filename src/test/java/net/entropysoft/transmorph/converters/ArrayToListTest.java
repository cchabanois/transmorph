package net.entropysoft.transmorph.converters;

import java.util.LinkedList;
import java.util.List;

import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterTest;
import junit.framework.TestCase;

public class ArrayToListTest extends TestCase {

	public void testArrayOfPrimitivesToGenericList() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		converter.setUseInternalFormFullyQualifiedName(false);

		// int[] => List<Integer> (ArrayToListConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		List<Integer> listOfInts = (List<Integer>) converter.convert(
				arrayOfInts, List.class, new Class[] { Integer.class });
		assertEquals(6, listOfInts.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, listOfInts.get(i).intValue());
		}
	}

	public void testArrayOfPrimitivesToListInstance() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		// int[] => LinkedList<Integer> (ArrayToListConverter)
		LinkedList<Integer> linkedList = (LinkedList<Integer>) converter
				.convert(arrayOfInts, LinkedList.class,
						new Class[] { Integer.class });
		assertNotNull(linkedList);
		assertEquals(6, linkedList.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, linkedList.get(i).intValue());
		}

	}

	public void testArrayToGenericListUnbounded() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		// int[] => List<*> (ArrayToListConverter)
		List<?> arrayOfSomething = (List<?>) converter.convert(arrayOfInts,
				"Ljava.util.List<*>;");
		assertNotNull(arrayOfSomething);
		assertEquals(6, arrayOfSomething.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, arrayOfSomething.get(i));
		}		
	}
	
	public void testArrayToGenericListUpperbound() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		// String[] => List<? extends Number> (ArrayToListConverter)
		String[] arrayOfStrings = new String[] { "0", "1", "2", "3", "4", "5" };
		List<? extends Number> listOfNumbers = (List<? extends Number>) converter
				.convert(arrayOfStrings,
						"Ljava.util.List<+Ljava.lang.Number;>;");
		assertNotNull(listOfNumbers);
		assertEquals(6, listOfNumbers.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, listOfNumbers.get(i).intValue());
		}
	}

}
