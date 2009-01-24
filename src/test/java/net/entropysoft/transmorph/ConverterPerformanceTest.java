package net.entropysoft.transmorph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.entropysoft.transmorph.converters.ArrayToArray;
import net.entropysoft.transmorph.converters.ArrayToCollection;
import net.entropysoft.transmorph.converters.CalendarToDate;
import net.entropysoft.transmorph.converters.CharacterArrayToString;
import net.entropysoft.transmorph.converters.CollectionToArray;
import net.entropysoft.transmorph.converters.CollectionToCollection;
import net.entropysoft.transmorph.converters.DateToCalendar;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.MapToMap;
import net.entropysoft.transmorph.converters.NumberToNumber;
import net.entropysoft.transmorph.converters.ObjectToString;
import net.entropysoft.transmorph.converters.StringToBoolean;
import net.entropysoft.transmorph.converters.StringToCharacterArray;
import net.entropysoft.transmorph.converters.StringToClass;
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

public class ConverterPerformanceTest extends TestCase {

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
		MapToMap mapToMap = new MapToMap();
		mapToMap.setKeyConverter(new StringToNumber());
		ArrayToCollection arrayToCollection = new ArrayToCollection();
		arrayToCollection.setElementConverter(new IdentityConverter());
		mapToMap.setValueConverter(arrayToCollection);

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { mapToMap });
		converter.setUseInternalFormFullyQualifiedName(false);

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
				converter,
				map,
				"Ljava.util.Map<Ljava.lang.Integer;Ljava.util.List<Ljava.lang.String;>;>;",
				10, 40, new IManualConversion() {

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

	private void runConverter(String testName, Converter converter,
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

	private void runConverter(String testName, Converter converter,
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
