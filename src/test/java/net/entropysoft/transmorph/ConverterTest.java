package net.entropysoft.transmorph;

import java.io.File;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.namespace.QName;

import junit.framework.TestCase;
import net.entropysoft.transmorph.converters.AbstractSimpleConverter;
import net.entropysoft.transmorph.converters.ArrayToArray;
import net.entropysoft.transmorph.converters.ArrayToCollection;
import net.entropysoft.transmorph.converters.CalendarToDate;
import net.entropysoft.transmorph.converters.CharacterArrayToString;
import net.entropysoft.transmorph.converters.CollectionToArray;
import net.entropysoft.transmorph.converters.CollectionToCollection;
import net.entropysoft.transmorph.converters.DateToCalendar;
import net.entropysoft.transmorph.converters.FormattedStringToNumber;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.MapToMap;
import net.entropysoft.transmorph.converters.MultiStepConverter;
import net.entropysoft.transmorph.converters.NumberToNumber;
import net.entropysoft.transmorph.converters.ObjectToFormattedString;
import net.entropysoft.transmorph.converters.ObjectToObjectUsingConstructor;
import net.entropysoft.transmorph.converters.ObjectToString;
import net.entropysoft.transmorph.converters.SingleElementToArray;
import net.entropysoft.transmorph.converters.SingleElementToCollection;
import net.entropysoft.transmorph.converters.StringToBoolean;
import net.entropysoft.transmorph.converters.StringToCalendar;
import net.entropysoft.transmorph.converters.StringToCharacterArray;
import net.entropysoft.transmorph.converters.StringToClass;
import net.entropysoft.transmorph.converters.StringToDate;
import net.entropysoft.transmorph.converters.StringToEnum;
import net.entropysoft.transmorph.converters.StringToFile;
import net.entropysoft.transmorph.converters.StringToNumber;
import net.entropysoft.transmorph.converters.StringToQName;
import net.entropysoft.transmorph.converters.StringToStringBuffer;
import net.entropysoft.transmorph.converters.StringToStringBuilder;
import net.entropysoft.transmorph.converters.StringToTimeZone;
import net.entropysoft.transmorph.converters.StringToURI;
import net.entropysoft.transmorph.converters.StringToURL;
import net.entropysoft.transmorph.converters.URIToURL;
import net.entropysoft.transmorph.converters.URLToURI;
import net.entropysoft.transmorph.converters.beans.BeanPropertyTypeProvider;
import net.entropysoft.transmorph.converters.beans.BeanToBean;
import net.entropysoft.transmorph.converters.beans.BeanToBeanMapping;
import net.entropysoft.transmorph.converters.beans.MapToBean;
import net.entropysoft.transmorph.modifiers.CanonicalizeFile;
import net.entropysoft.transmorph.modifiers.IModifier;
import net.entropysoft.transmorph.modifiers.TrimString;
import net.entropysoft.transmorph.modifiers.UppercaseString;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeFactory;

public class ConverterTest extends TestCase {

	private final static IConverter[] converters = new IConverter[] {
			new NumberToNumber(), new StringToNumber(), new StringToBoolean(),
			new StringToEnum(), new StringToStringBuffer(),
			new StringToStringBuilder(), new StringToClass(),
			new ArrayToArray(), new MapToMap(), new ArrayToCollection(),
			new CollectionToCollection(), new CollectionToArray(),
			new StringToFile(), new StringToURL(), new StringToURI(),
			new URIToURL(), new URLToURI(), new CharacterArrayToString(),
			new StringToCharacterArray(), new StringToQName(),
			new StringToTimeZone(), new ObjectToString(), new DateToCalendar(),
			new CalendarToDate(), new IdentityConverter() };

	public void testMapToMap() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);
		converter.setUseInternalFormFullyQualifiedName(false);

		// Map[String, String[]] => Map<String,List<String>> (MapToMapConverter
		// and ArrayToListConverter)
		Map map = new HashMap();
		map.put("key1", new String[] { "value1-1", "value1-2" });
		map.put("key2", new String[] { "value2-1", "value2-2" });
		map.put("key3", null);
		map.put("key4", new String[] { null, null });
		map.put(null, new String[] { "value5-1", "value5-2" });
		Map<String, List<String>> converted = (Map<String, List<String>>) converter
				.convert(map,
						"Ljava.util.Map<Ljava.lang.String;Ljava.util.List<Ljava.lang.String;>;>;");
		List<String> list1 = converted.get("key1");
		assertEquals("value1-1", list1.get(0));
		assertEquals("value1-2", list1.get(1));
		List<String> list2 = converted.get("key2");
		assertEquals("value2-1", list2.get(0));
		assertEquals("value2-2", list2.get(1));
		List<String> list3 = converted.get("key3");
		assertEquals(null, list3);
		List<String> list4 = converted.get("key4");
		assertEquals(null, list4.get(0));
		assertEquals(null, list4.get(1));
		assertTrue(converted.containsKey(null));
		List<String> list5 = converted.get(null);
		assertEquals("value5-1", list5.get(0));
		assertEquals("value5-2", list5.get(1));
	}

	public void testMapToProperties() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("key1", 1);
		map.put("key2", 2);

		Properties properties = (Properties) converter.convert(map,
				Properties.class);
		assertNotNull(properties);
		assertEquals("1", properties.get("key1"));
		assertEquals("2", properties.get("key2"));
	}

	public void testArrayToList() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);
		converter.setUseInternalFormFullyQualifiedName(false);

		// int[] => List<Integer> (ArrayToListConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		List<Integer> listOfInts = (List<Integer>) converter.convert(
				arrayOfInts, List.class, new Class[] { Integer.class });
		assertEquals(6, listOfInts.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, listOfInts.get(i).intValue());
		}

		// int[] => List<*> (ArrayToListConverter)
		List<?> arrayOfSomething = (List<?>) converter.convert(arrayOfInts,
				"Ljava.util.List<*>;");
		assertNotNull(arrayOfSomething);
		assertEquals(6, arrayOfSomething.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, arrayOfSomething.get(i));
		}

		// int[] => LinkedList<Integer> (ArrayToListConverter)
		LinkedList<Integer> linkedList = (LinkedList<Integer>) converter
				.convert(arrayOfInts, LinkedList.class,
						new Class[] { Integer.class });
		assertNotNull(linkedList);
		assertEquals(6, linkedList.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, linkedList.get(i).intValue());
		}

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

	public void testArrayToArray() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		// Object[] => String[] (ArrayToArrayConverter)
		Object[] arrayOfObjects = new Object[] { "one", "two", "three" };
		String[] arrayOfStrings = (String[]) converter.convert(arrayOfObjects,
				(new String[0]).getClass());
		assertEquals("one", arrayOfStrings[0]);
		assertEquals("two", arrayOfStrings[1]);
		assertEquals("three", arrayOfStrings[2]);

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

	public void testStringToNumber() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		// String => int (StringToNumberConverter)
		String myStr = "50";
		int myInt = (Integer) converter.convert(myStr, Integer.TYPE);
		assertEquals(50, myInt);

		// String => BigDecimal (StringToNumberConverter)
		BigDecimal bigDecimal = (BigDecimal) converter
				.convert("5.56564546546464646577775612321443244664456",
						BigDecimal.class);
		assertEquals("5.56564546546464646577775612321443244664456", bigDecimal
				.toString());

		Number number = (Number) converter.convert(
				"5.56564546546464646577775612321443244664456", Number.class);
		assertNotNull(number);
		assertTrue(number instanceof Double);
	}

	public void testStringToBoolean() throws Exception {
		StringToBoolean stringToBoolean = new StringToBoolean();
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { stringToBoolean,
				new IdentityConverter() });

		// String => boolean (StringToBooleanConverter)
		boolean myBoolean = (Boolean) converter.convert("false", Boolean.TYPE);
		assertEquals(false, myBoolean);
		myBoolean = (Boolean) converter.convert("true", Boolean.TYPE);
		assertEquals(true, myBoolean);

		try {
			myBoolean = (Boolean) converter.convert("faux", Boolean.TYPE);
			fail("Should not have been converted");
		} catch (ConverterException e) {
		}
		stringToBoolean.setCaseSensitive(false);
		stringToBoolean.setTrueString("vrai");
		stringToBoolean.setFalseString("faux");

		myBoolean = (Boolean) converter.convert("Faux", Boolean.TYPE);
		assertEquals(false, myBoolean);

		myBoolean = (Boolean) converter.convert("Vrai", Boolean.TYPE);
		assertEquals(true, myBoolean);

		try {
			myBoolean = (Boolean) converter.convert(null, Boolean.TYPE);
			fail("Should not have been converted");
		} catch (ConverterException e) {
		}

		Boolean booleanObject = (Boolean) converter
				.convert(null, Boolean.class);
		assertNull(booleanObject);
	}

	public void testArrayToCollection() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		// int[] => Collection<Integer> (ArrayToCollectionConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		Collection<Integer> collectionOfInts = (Collection<Integer>) converter
				.convert(arrayOfInts, Collection.class,
						new Class[] { Integer.class });
		assertEquals(6, collectionOfInts.size());
		int j = 0;
		for (int i : collectionOfInts) {
			assertEquals(j, i);
			j++;
		}
	}

	public void testCollectionToCollection() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		List<Integer> source = new ArrayList<Integer>();
		source.add(30);
		source.add(40);
		source.add(50);

		Set<String> set = (Set<String>) converter.convert(source, Set.class,
				new Class[] { String.class });
		assertNotNull(set);

		LinkedHashSet<String> linkedHashSet = (LinkedHashSet<String>) converter
				.convert(source, LinkedHashSet.class,
						new Class[] { String.class });
		assertNotNull(linkedHashSet);
	}

	public void testCollectionToArray() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		List<Integer> source = new ArrayList<Integer>();
		source.add(30);
		source.add(40);
		source.add(50);

		String[] array = (String[]) converter.convert(source, String[].class);
		assertNotNull(array);
		assertEquals(3, array.length);
		assertEquals("30", array[0]);
		assertEquals("40", array[1]);
		assertEquals("50", array[2]);

		List<List<Integer>> listListInteger = new ArrayList<List<Integer>>();
		List<Integer> listInteger = new ArrayList<Integer>();
		listInteger.add(11);
		listInteger.add(12);
		listInteger.add(13);
		listListInteger.add(listInteger);

		listInteger = new ArrayList<Integer>();
		listInteger.add(21);
		listInteger.add(22);
		listInteger.add(23);
		listListInteger.add(listInteger);

		String[][] array2D = (String[][]) converter.convert(listListInteger,
				String[][].class);
		assertEquals("11", array2D[0][0]);
		assertEquals("12", array2D[0][1]);
		assertEquals("13", array2D[0][2]);
		assertEquals("21", array2D[1][0]);
		assertEquals("22", array2D[1][1]);
		assertEquals("23", array2D[1][2]);
	}

	public void testArrayToSet() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		// int[] => Set<Integer> (ArrayToSetConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		Set<Integer> setOfInts = (Set<Integer>) converter.convert(arrayOfInts,
				Set.class, new Class[] { Integer.class });
		assertEquals(6, setOfInts.size());
		for (int i = 0; i < 6; i++) {
			assertTrue(setOfInts.contains(i));
		}
	}

	public void testNumberToNumber() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		// int => long (NumberToNumberConverter)
		int myInt = 55;
		long myLong = (Long) converter.convert(myInt, Long.TYPE);
		assertEquals(55, myLong);

		try {
			myLong = (Long) converter.convert(null, Long.TYPE);
			fail("Should not have been able to convert");
		} catch (ConverterException e) {

		}
		Long myLongWrapper = (Long) converter.convert(null, Long.class);
		assertEquals(null, myLongWrapper);

		// int => Long (NumberToNumberConverter)
		assertEquals(new Long(55), converter.convert(55, Long.class));

		// int => BigInteger (NumberToNumberConverter)
		assertEquals(BigInteger.valueOf(55), (BigInteger) converter.convert(55,
				BigInteger.class));

		assertEquals((byte) 1, converter.convert(257, Byte.TYPE));
	}

	public void testStringToFile() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		File file = (File) converter.convert("c:\\temp", File.class);
		assertNotNull(file);
		assertEquals("c:\\temp", file.toString());
	}

	public void testStringToFileCanonical() throws Exception {
		StringToFile stringToFile = new StringToFile();
		stringToFile.setModifiers(new IModifier[] { new CanonicalizeFile() });
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { stringToFile });
		File file = (File) converter.convert("temp", File.class);
		assertNotNull(file);
		// getCanonicalFile is OS-dependant
		assertEquals((new File("temp")).getCanonicalFile(), file);
	}

	public void testCharacterArrayToString() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		String str = (String) converter.convert(new char[] { 'h', 'e', 'l',
				'l', 'o' }, String.class);
		assertNotNull(str);
		assertEquals("hello", str);

	}

	public void testStringToCharacterArray() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		char[] chars = (char[]) converter.convert("hello", char[].class);
		assertNotNull(chars);
		assertEquals("hello".length(), chars.length);
	}

	public void testStringToURL() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		URL url = (URL) converter.convert("http://www.entropysoft.net",
				URL.class);
		assertNotNull(url);
		assertEquals("http://www.entropysoft.net", url.toString());

		try {
			url = (URL) converter.convert("httpa://www.entropysoft.net",
					URL.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}

	public void testStringToURI() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		URI uri = (URI) converter.convert("http://www.entropysoft.net",
				URI.class);
		assertNotNull(uri);
		assertEquals("http://www.entropysoft.net", uri.toString());
	}

	public void testURIToURL() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		URL url = (URL) converter.convert(
				new URI("http://www.entropysoft.net"), URL.class);
		assertNotNull(url);
		assertEquals("http://www.entropysoft.net", url.toString());
	}

	public void testURLToURI() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		URI uri = (URI) converter.convert(
				new URL("http://www.entropysoft.net"), URI.class);
		assertNotNull(uri);
		assertEquals("http://www.entropysoft.net", uri.toString());
	}

	public void testStringToDate() throws Exception {
		StringToDate stringToDateConverter1 = new StringToDate();
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		simpleDateFormat1.setLenient(false);
		stringToDateConverter1.setDateFormat(simpleDateFormat1);

		StringToDate stringToDateConverter2 = new StringToDate();
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
		simpleDateFormat2.setLenient(false);
		stringToDateConverter2.setDateFormat(simpleDateFormat2);

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { stringToDateConverter1,
				stringToDateConverter2 });
		Date date = (Date) converter.convert("29/12/2008", Date.class);
		assertNotNull(date);

		date = (Date) converter.convert("2009", Date.class);
		assertNotNull(date);

		try {
			date = (Date) converter.convert("200A", Date.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}

	public void testDateToCalendar() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { new DateToCalendar() });

		Calendar calendar = (Calendar) converter.convert(new Date(0),
				Calendar.class);
		assertNotNull(calendar);
		assertEquals(1970, calendar.get(Calendar.YEAR));
	}

	public void testStringToCalendar() throws Exception {
		StringToCalendar stringToCalendarConverter1 = new StringToCalendar();
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		simpleDateFormat1.setLenient(false);
		stringToCalendarConverter1.setDateFormat(simpleDateFormat1);

		StringToCalendar stringToCalendarConverter2 = new StringToCalendar();
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
		simpleDateFormat2.setLenient(false);
		stringToCalendarConverter2.setDateFormat(simpleDateFormat2);

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] {
				stringToCalendarConverter1, stringToCalendarConverter2 });
		Calendar calendar = (Calendar) converter.convert("29/12/2008",
				Calendar.class);
		assertNotNull(calendar);
		assertEquals(2008, calendar.get(Calendar.YEAR));
		assertEquals(11, calendar.get(Calendar.MONTH));
		assertEquals(29, calendar.get(Calendar.DAY_OF_MONTH));

		calendar = (Calendar) converter.convert("2009", Calendar.class);
		assertNotNull(calendar);
		assertEquals(2009, calendar.get(Calendar.YEAR));

		try {
			calendar = (Calendar) converter.convert("200A", Calendar.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}

	public void testStringToQName() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		QName qname = (QName) converter.convert(
				"{http://www.entropysoft.ney}localPart", QName.class);
		assertNotNull(qname);
	}

	public void testStringToTimeZone() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		String[] ids = TimeZone.getAvailableIDs();

		TimeZone timezone = (TimeZone) converter.convert("Asia/Kuala_Lumpur",
				TimeZone.class);
		assertNotNull(timezone);
	}

	public void testFormattedStringToNumber() throws Exception {
		FormattedStringToNumber formattedStringToNumberConverter = new FormattedStringToNumber();
		NumberFormat numberFormat = NumberFormat
				.getNumberInstance(Locale.FRENCH);
		formattedStringToNumberConverter.setNumberFormat(numberFormat);

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(),
				new IConverter[] { formattedStringToNumberConverter });

		float result = (Float) converter.convert("-22,33", Float.TYPE);
		assertNotNull(result);
		assertEquals(-22.33, result, 0.001);

		try {
			result = (Float) converter.convert("-22,33A", Float.TYPE);
			fail("Should not convert");
		} catch (ConverterException e) {

		}

		try {
			result = (Float) converter.convert(null, Float.TYPE);
			fail("Should not convert");
		} catch (ConverterException e) {

		}

		assertNull(converter.convert(null, Float.class));
	}

	public void testStringToEnum() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		RetentionPolicy retentionPolicy = (RetentionPolicy) converter.convert(
				"CLASS", RetentionPolicy.class);
		assertEquals(RetentionPolicy.CLASS, retentionPolicy);

		try {
			retentionPolicy = (RetentionPolicy) converter.convert("class",
					RetentionPolicy.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}

	public void testStringToClass() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		Class clazz = (Class) converter.convert(this.getClass().getName(),
				Class.class);
		assertEquals(this.getClass(), clazz);
	}

	public void testObjectToObjectUsingConstructor() throws Exception {
		ObjectToObjectUsingConstructor objectToObjectUsingConstructor = new ObjectToObjectUsingConstructor();

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(),
				new IConverter[] { objectToObjectUsingConstructor });
		File file = (File) converter.convert("c:\temp", File.class);
		assertNotNull(file);

		objectToObjectUsingConstructor
				.setHandledDestinationClasses(new Class[] { URL.class });
		try {
			file = (File) converter.convert("c:\temp", File.class);
			fail("Convertion should have failed");
		} catch (ConverterException e) {

		}

		URL url = (URL) converter.convert("http://www.entropysoft.net",
				URL.class);
		assertEquals("http://www.entropysoft.net", url.toString());
	}

	public void testIdentity() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		assertSame("my string", converter.convert("my string", String.class));

		// this will be handled by NumberToNumber
		Integer myInteger = new Integer(80);
		assertSame(myInteger, converter.convert(myInteger, Integer.class));

		// an other array list will be created ...
		ArrayList arrayList = new ArrayList();
		arrayList.add(80);
		assertEquals(arrayList, converter.convert(arrayList, List.class));
	}

	public void testSingleElementToArray() throws Exception {
		IConverter[] converters = new IConverter[] { new NumberToNumber(),
				new StringToNumber(), new StringToBoolean(),
				new StringToEnum(), new ArrayToArray(), new MapToMap(),
				new ArrayToCollection(), new CollectionToCollection(),
				new ObjectToString(), new SingleElementToArray(),
				new IdentityConverter() };

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		int[] array = (int[]) converter.convert("55", int[].class);
		assertNotNull(array);
		assertEquals(1, array.length);
		assertEquals(55, array[0]);
	}

	public void testSingleElementToCollection() throws Exception {
		IConverter[] converters = new IConverter[] { new NumberToNumber(),
				new StringToNumber(), new StringToBoolean(),
				new StringToEnum(), new ArrayToArray(), new MapToMap(),
				new ArrayToCollection(), new CollectionToCollection(),
				new ObjectToString(), new SingleElementToCollection(),
				new IdentityConverter() };

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		List<Integer> list = (List<Integer>) converter.convert("55",
				List.class, new Class[] { Integer.class });
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals(55, list.get(0).intValue());
	}

	public void testStringToStringBuffer() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);
		StringBuffer sb = (StringBuffer) converter.convert("My string",
				StringBuffer.class);
		assertEquals("My string", sb.toString());
	}

	public void testStringToStringBuilder() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);
		StringBuilder sb = (StringBuilder) converter.convert("My string",
				StringBuilder.class);
		assertEquals("My string", sb.toString());
	}

	public void testObjectToString() throws Exception {
		ObjectToString objectToString = new ObjectToString();

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { objectToString });
		String str = (String) converter.convert(new URL(
				"http://www.entropysoft.net"), String.class);
		assertEquals("http://www.entropysoft.net", str);
		str = (String) converter.convert(new File("c:\temp"), String.class);
		assertEquals("c:\temp", str);

		objectToString.setHandledSourceClasses(new Class[] { URL.class });
		try {
			str = (String) converter.convert(new File("c:\temp"), String.class);
			fail("Convertion should have failed");
		} catch (ConverterException e) {

		}

		str = (String) converter.convert(new URL("http://www.entropysoft.net"),
				String.class);
		assertEquals("http://www.entropysoft.net", str);
	}

	public void testObjectToFormattedString() throws Exception {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				Locale.FRANCE);

		ObjectToFormattedString objectToFormattedStringConverter = new ObjectToFormattedString(
				Date.class, df);

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(),
				new IConverter[] { objectToFormattedStringConverter });

		Calendar calendar = Calendar.getInstance(Locale.FRANCE);
		calendar.set(2009, 0, 1, 0, 0, 0);
		String str = (String) converter.convert(calendar.getTime(),
				String.class);
		assertNotNull(str);
		assertEquals("01/01/09", str);

		objectToFormattedStringConverter = new ObjectToFormattedString(
				Calendar.class, Date.class, df);

		converter = new Converter(ConverterTest.class.getClassLoader(),
				new IConverter[] { objectToFormattedStringConverter,
						new CalendarToDate() });

		calendar = Calendar.getInstance(Locale.FRANCE);
		calendar.set(2009, 0, 1, 0, 0, 0);
		str = (String) converter.convert(calendar, String.class);
		assertNotNull(str);
		assertEquals("01/01/09", str);
	}

	public void testMultiStepConverter() throws Exception {
		TypeFactory typeFactory = new TypeFactory(ConverterTest.class
				.getClassLoader());

		MultiStepConverter multiStepConverter = new MultiStepConverter(
				new Type[] { typeFactory.getType(String.class),
						typeFactory.getType(Integer.class),
						typeFactory.getType(Boolean.TYPE) });

		IConverter intToBoolean = new AbstractSimpleConverter(Integer.class,
				Boolean.TYPE) {

			@Override
			public Object doConvert(Object sourceObject, Type destinationType)
					throws ConverterException {
				int theInt = ((Number) sourceObject).intValue();
				if (theInt == 0) {
					return false;
				} else {
					return true;
				}
			}

		};

		IConverter[] converters = new IConverter[] { new StringToNumber(),
				new NumberToNumber(), intToBoolean, multiStepConverter };

		Converter converter = new Converter(typeFactory, converters);
		assertTrue((Boolean) converter.convert("22", Boolean.TYPE));
		assertFalse((Boolean) converter.convert("0", Boolean.TYPE));
	}

	public void testMultiStepWithConverters() throws Exception {
		TypeFactory typeFactory = new TypeFactory(ConverterTest.class
				.getClassLoader());

		// we will convert an array of Dates to a List of uppercased strings

		// use an ObjectToFormattedString to convert Date to string
		ObjectToFormattedString dateToString = new ObjectToFormattedString(
				Date.class, new SimpleDateFormat("EEE, MMM d, yy", Locale.US));
		dateToString.setModifiers(new IModifier[] { new UppercaseString() });

		ArrayToCollection arrayToCollection = new ArrayToCollection();
		arrayToCollection.setElementConverter(dateToString);

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { arrayToCollection });

		Date[] dates = new Date[] { new Date(0), new Date(1232621965342L) };
		List<String> listOfstrings = (List<String>) converter.convert(dates,
				List.class, new Class[] { String.class });
		assertEquals("THU, JAN 1, 70", listOfstrings.get(0));
		assertEquals("THU, JAN 22, 09", listOfstrings.get(1));
	}

	public void testTrimString() throws Exception {
		IdentityConverter identityConverter = new IdentityConverter();
		identityConverter.setModifiers(new IModifier[] { new TrimString() });
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { identityConverter });
		String converted = (String) converter
				.convert(
						"    This is a string with leading and trailing white spaces    ",
						String.class);
		assertEquals("This is a string with leading and trailing white spaces",
				converted);
	}

	public void testBeanToBean() throws Exception {
		BeanToBean beanToBean = new BeanToBean();
		IConverter[] converters = new IConverter[] { new NumberToNumber(),
				new StringToNumber(), new StringToClass(), new ArrayToArray(),
				new MapToMap(), new ArrayToCollection(),
				new CollectionToCollection(), new CollectionToArray(),
				new StringToFile(), new StringToURL(),
				new CharacterArrayToString(), new StringToCharacterArray(),
				new ObjectToString(), new DateToCalendar(),
				new IdentityConverter(), beanToBean };
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		BeanToBeanMapping beanToBeanMapping = new BeanToBeanMapping(
				MyBean4.class, MyBean4TransferObject.class);
		beanToBeanMapping.addMapping("size", "length");
		beanToBean.addBeanToBeanMapping(beanToBeanMapping);

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("hello world");
		myBean4.setSize(55);
		List<String> myStrings = new ArrayList<String>();
		myStrings.add("first");
		myStrings.add("second");
		myStrings.add("third");
		myBean4.setMyStrings(myStrings);

		MyBean4TransferObject myBean4TransferObject = (MyBean4TransferObject) converter
				.convert(myBean4, MyBean4TransferObject.class);
		assertEquals("hello world", myBean4TransferObject.getMyString());
		assertEquals("first", myBean4TransferObject.getMyStrings()[0]);
		assertEquals("second", myBean4TransferObject.getMyStrings()[1]);
		assertEquals("third", myBean4TransferObject.getMyStrings()[2]);
		assertEquals(55, myBean4TransferObject.getLength());
	}

	public void testMapToBean() throws Exception {
		TypeFactory typeFactory = new TypeFactory(ConverterTest.class
				.getClassLoader());

		MapToBean mapToBean = new MapToBean();
		BeanPropertyTypeProvider beanDestinationPropertyTypeProvider = new BeanPropertyTypeProvider();
		beanDestinationPropertyTypeProvider.setPropertyDestinationType(
				MyBean3.class, "myList", typeFactory.getType(List.class,
						new Class[] { String.class }));
		mapToBean
				.setBeanPropertyTypeProvider(beanDestinationPropertyTypeProvider);

		IConverter[] converters = new IConverter[] { new NumberToNumber(),
				new StringToNumber(), new StringToClass(), new ArrayToArray(),
				new MapToMap(), new ArrayToCollection(),
				new CollectionToCollection(), new StringToFile(),
				new StringToURL(), new CharacterArrayToString(),
				new StringToCharacterArray(), new ObjectToString(),
				new DateToCalendar(), new IdentityConverter(), mapToBean };

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);
		Map<String, Object> mapBean1 = new HashMap<String, Object>();
		mapBean1.put("myInt", "15");
		mapBean1.put("myListOfStrings", new int[] { 1, 2, 3 });

		Map<String, Object> mapBean2 = new HashMap<String, Object>();
		mapBean2.put("myString", new Float(55.2));
		mapBean1.put("myBean2", mapBean2);

		Map<String, Object> mapBean3 = new HashMap<String, Object>();
		mapBean3.put("myList", new int[] { 1, 2, 3 });
		mapBean1.put("myBean3", mapBean3);

		MyBean1 myBean1 = (MyBean1) converter.convert(mapBean1, MyBean1.class);
		assertNotNull(myBean1);
		assertEquals(15, myBean1.getMyInt());
		List<String> listOfStrings = myBean1.getMyListOfStrings();
		assertEquals("1", listOfStrings.get(0));
		assertEquals("2", listOfStrings.get(1));
		assertEquals("3", listOfStrings.get(2));
		MyBean2 myBean2 = myBean1.getMyBean2();
		assertEquals("55.2", myBean2.getMyString());
		MyBean3 myBean3 = myBean1.getMyBean3();
		listOfStrings = myBean3.getMyList();
		assertEquals("1", listOfStrings.get(0));
		assertEquals("2", listOfStrings.get(1));
		assertEquals("3", listOfStrings.get(2));
	}

	public static class MyBean1 {
		private int myInt;
		private List<String> myListOfStrings;
		private MyBean2 myBean2;
		private MyBean3 myBean3;

		public int getMyInt() {
			return myInt;
		}

		public void setMyInt(int myInt) {
			this.myInt = myInt;
		}

		public List<String> getMyListOfStrings() {
			return myListOfStrings;
		}

		public void setMyListOfStrings(List<String> myListOfStrings) {
			this.myListOfStrings = myListOfStrings;
		}

		public MyBean2 getMyBean2() {
			return myBean2;
		}

		public void setMyBean2(MyBean2 myBean2) {
			this.myBean2 = myBean2;
		}

		public MyBean3 getMyBean3() {
			return myBean3;
		}

		public void setMyBean3(MyBean3 myBean3) {
			this.myBean3 = myBean3;
		}

	}

	public static class MyBean2 {
		private String myString;

		public String getMyString() {
			return myString;
		}

		public void setMyString(String myString) {
			this.myString = myString;
		}

	}

	public static class MyBean3 {
		private List myList;

		public List getMyList() {
			return myList;
		}

		public void setMyList(List myList) {
			this.myList = myList;
		}

	}

	public static class MyBean4Ancestor {
		private int size;

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

	}

	public static class MyBean4 extends MyBean4Ancestor {
		private String myString;
		private List<String> myStrings;

		public String getMyString() {
			return myString;
		}

		public void setMyString(String myString) {
			this.myString = myString;
		}

		public List<String> getMyStrings() {
			return myStrings;
		}

		public void setMyStrings(List<String> myStrings) {
			this.myStrings = myStrings;
		}
	}

	public static class MyBean4TransferObject implements java.io.Serializable {

		private static final long serialVersionUID = 4859101895339173273L;

		private String myString;
		private String[] myStrings;
		private long length;

		public String getMyString() {
			return myString;
		}

		public void setMyString(String myString) {
			this.myString = myString;
		}

		public String[] getMyStrings() {
			return myStrings;
		}

		public void setMyStrings(String[] myStrings) {
			this.myStrings = myStrings;
		}

		public long getLength() {
			return length;
		}

		public void setLength(long length) {
			this.length = length;
		}

	}

}
