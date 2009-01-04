/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.entropysoft.transmorph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cedric
 */
public class TestPerf {

	private final static IConverter[] converters = new IConverter[] {
			Converters.numberToNumber, Converters.stringToNumber,
			Converters.stringToBoolean, Converters.stringToEnum,
			Converters.stringToStringBuffer, Converters.stringToStringBuilder,
			Converters.stringToClass, Converters.arrayToArray,
			Converters.mapToMap, Converters.arrayToCollection,
			Converters.collectionToCollection, Converters.collectionToArray,
			Converters.stringToFile, Converters.stringToURL,
			Converters.stringToURI, Converters.uriToUrl, Converters.urlToUri,
			Converters.characterArrayToString, Converters.stringToCharacterArray,
			Converters.objectToString, Converters.dateToCalendar,
			Converters.calendarToDate, Converters.identityConverter };


    public static void main(String[] args) throws Exception  {
		Converter converter = Converter.getConverter(TestPerf.class
				.getClassLoader(), converters);

		// Map[String, String[]] => Map<String,List<String>> (MapToMapConverter
		// and ArrayToListConverter)
		Map map = new HashMap();
		map.put("key1", new String[] { "value1-1", "value1-2" });
		map.put("key2", new String[] { "value2-1", "value2-2" });
		map.put("key3", null);
		map.put("key4", new String[] { null, null });
		map.put(null, new String[] { "value5-1", "value5-2" });
		for (int i = 0; i < 10000; i++) {
        Map<String, List<String>> converted = (Map<String, List<String>>) converter
				.convert(map,
						"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
        }
        
    }

}
