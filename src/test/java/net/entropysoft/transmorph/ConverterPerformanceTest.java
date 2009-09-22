package net.entropysoft.transmorph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;
import net.entropysoft.transmorph.converters.StringToBoolean;
import net.entropysoft.transmorph.type.TypeReference;

public class ConverterPerformanceTest extends TestCase {
	private Random random = new Random();

	public void testListOfStringToArrayOfBoolean() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		Transmorph transmorph = new Transmorph(defaultConverters);
		StringToBoolean stringToBoolean = defaultConverters
				.getStringToBoolean();
		stringToBoolean.setTrueString("vrai");
		stringToBoolean.setFalseString("faux");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 100000; i++) {
			if (random.nextDouble() < 0.5) {
				list.add("vrai");
			} else {
				list.add("faux");
			}
		}
		TypeReference<boolean[]> typeReference = new TypeReference<boolean[]>() {
		};

		runConverter("testListToArray", transmorph, list, typeReference, 10,
				150, new IManualConversion() {

					public Object convert(Object sourceObject) {
						List<String> list = (List<String>) sourceObject;
						boolean[] result = new boolean[list.size()];
						int i = 0;
						for (String str : list) {
							if (str.equalsIgnoreCase("vrai")) {
								result[i] = true;
							} else {
								result[i] = false;
							}
							i++;
						}
						return result;
					}

				});
	}

	public void testArrayOfIntsToListOfStrings() throws Exception {
		// ArrayToCollection arrayToCollection = new ArrayToCollection();
		// arrayToCollection.setElementConverter(new ObjectToString());
		// Transmorph transmorph = new Transmorph(ConverterTest.class
		// .getClassLoader(), arrayToCollection);

		Transmorph transmorph = new Transmorph(new DefaultConverters());
		int[] array = new int[100000];
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}

		TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
		};

		runConverter("testArrayToList", transmorph, array, typeReference, 10,
				200, new IManualConversion() {

					public Object convert(Object sourceObject) {
						int[] array = (int[]) sourceObject;
						String[] result = new String[array.length];
						for (int i = 0; i < array.length; i++) {
							result[i] = Integer.toString(array[i]);
						}
						return result;
					}

				});

	}

	public void testMapToMapArrayToList() throws Exception {
		// MapToMap mapToMap = new MapToMap();
		// mapToMap.setKeyConverter(new StringToNumber());
		// ArrayToCollection arrayToCollection = new ArrayToCollection();
		// arrayToCollection.setElementConverter(new
		// ImmutableIdentityConverter());
		// mapToMap.setValueConverter(arrayToCollection);
		//
		// Transmorph transmorph = new Transmorph(ConverterTest.class
		// .getClassLoader(), mapToMap);

		Transmorph transmorph = new Transmorph(new DefaultConverters());

		// Map[String, String[]] => Map<Integer,List<String>>
		Map map = new HashMap();
		for (int i = 0; i < 1000; i++) {
			String[] values = new String[100];
			for (int j = 0; j < values.length; j++) {
				values[j] = MessageFormat.format("value{0}-{1}", i, j);
			}
			map.put(Integer.toString(i), values);
		}
		TypeReference<Map<Integer, List<String>>> typeReference = new TypeReference<Map<Integer, List<String>>>() {
		};
		runConverter("testMapToMap", transmorph, map, typeReference, 20, 150,
				new IManualConversion() {

					public Object convert(Object sourceObject) {
						return mapToMapManual(sourceObject);
					}

				});
	}

	private Object mapToMapManual(Object sourceObject) {
		Map sourceMap = (Map) sourceObject;
		Map resultMap = new HashMap<Integer, List<String>>();
		for (Iterator it = sourceMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			String sourceMapKey = (String) mapEntry.getKey();
			String[] sourceMapValue = (String[]) mapEntry.getValue();
			Integer destMapKey = Integer.parseInt(sourceMapKey);
			List<String> destMapValue = new ArrayList<String>();
			for (int j = 0; j < sourceMapValue.length; j++) {
				destMapValue.add(sourceMapValue[j]);
			}
			resultMap.put(destMapKey, destMapValue);
		}
		return resultMap;
	}

	private interface IManualConversion {

		Object convert(Object sourceObject);

	}

	private void runConverter(String testName, Transmorph converter,
			Object sourceObject, TypeReference<?> destinationType,
			long numIterations, long maxTimeAllowedPerIteration,
			IManualConversion manualConversion) throws Exception {
		// warm up the converter
		converter.convert(sourceObject, destinationType);

		long startTime = System.nanoTime();

		for (int i = 0; i < numIterations; i++) {
			converter.convert(sourceObject, destinationType);
		}
		long stopTime = System.nanoTime();

		long elapsedMsPerIteration = (stopTime - startTime)
				/ (1000000 * numIterations);

		long elapsedMsPerIterationManual = 0;
		if (manualConversion != null) {
			long startTimeManual = System.nanoTime();

			for (int i = 0; i < numIterations; i++) {
				manualConversion.convert(sourceObject);
			}
			long stopTimeManual = System.nanoTime();

			elapsedMsPerIterationManual = (stopTimeManual - startTimeManual)
					/ (1000000 * numIterations);
		}

		if (elapsedMsPerIteration > maxTimeAllowedPerIteration) {
			fail(MessageFormat
					.format(
							"{0} took {1}ms while max allowed time was {2}ms. Manual conversion took {3}ms",
							testName, elapsedMsPerIteration,
							maxTimeAllowedPerIteration,
							elapsedMsPerIterationManual));
		} else {
			System.out.println(MessageFormat.format(
					"{0} took {1}ms (max allowed time was {2}ms)", testName,
					elapsedMsPerIteration, maxTimeAllowedPerIteration));
			if (manualConversion != null) {
				System.out.println(MessageFormat.format(
						"Manual conversion took {0} ms",
						elapsedMsPerIterationManual));
			}
		}
	}

}
