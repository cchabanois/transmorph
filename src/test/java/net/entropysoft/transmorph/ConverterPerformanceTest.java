package net.entropysoft.transmorph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class ConverterPerformanceTest extends TestCase {


	public void testMapToMapArrayToList() throws Exception {
//		MapToMap mapToMap = new MapToMap();
//		mapToMap.setKeyConverter(new StringToNumber());
//		ArrayToCollection arrayToCollection = new ArrayToCollection();
//		arrayToCollection.setElementConverter(new IdentityConverter());
//		mapToMap.setValueConverter(arrayToCollection);
//
//		Converter converter = new Converter(ConverterTest.class
//				.getClassLoader(), new Converters(mapToMap));

		Transmorph transmorph = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());
		
		
		transmorph.setUseInternalFormFullyQualifiedName(false);

		// Map[String, String[]] => Map<Integer,List<String>>
		Map map = new HashMap();
		for (int i = 0; i < 1000; i++) {
			String[] values = new String[100];
			for (int j = 0; j < values.length; j++) {
				values[j] = MessageFormat.format("value{0}-{1}", i, j);
			}
			map.put(Integer.toString(i), values);
		}
		runConverter(
				"testMapToMap",
				transmorph,
				map,
				"Ljava.util.Map<Ljava.lang.Integer;Ljava.util.List<Ljava.lang.String;>;>;",
				10, 60, new IManualConversion() {

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
			Object sourceObject, Class destinationClass,
			Class[] destinationClassTypeArgs, long numIterations,
			long maxTimeAllowedPerIteration) throws Exception {
		// warm up the converter
		converter.convert(sourceObject, destinationClass,
				destinationClassTypeArgs);

		// perform x number of additional mappings
		long startTime = System.nanoTime();

		for (int i = 0; i < numIterations; i++) {
			converter.convert(sourceObject, destinationClass,
					destinationClassTypeArgs);
		}
		long stopTime = System.nanoTime();

		long elapsedMsPerIteration = (stopTime - startTime)
				/ (1000000 * numIterations);

		if (elapsedMsPerIteration > maxTimeAllowedPerIteration) {
			fail(MessageFormat
					.format("{0} took {1}ms while max allowed time was {2}ms",
							testName, elapsedMsPerIteration,
							maxTimeAllowedPerIteration));
		}
	}

	private void runConverter(String testName, Transmorph converter,
			Object sourceObject, String parameterizedTypeSignature,
			long numIterations, long maxTimeAllowedPerIteration,
			IManualConversion manualConversion) throws Exception {
		// warm up the converter
		converter.convert(sourceObject, parameterizedTypeSignature);

		long startTime = System.nanoTime();

		for (int i = 0; i < numIterations; i++) {
			converter.convert(sourceObject, parameterizedTypeSignature);
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
					.format("{0} took {1}ms while max allowed time was {2}ms",
							testName, elapsedMsPerIteration,
							maxTimeAllowedPerIteration));
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
