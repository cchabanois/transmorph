package net.entropysoft.transmorph.converters;

import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterTest;
import junit.framework.TestCase;

public class CharacterToStringTest extends TestCase {

	public void testCharacterToString() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		char myChar = 'c';
		String str = (String) converter.convert(myChar, String.class);
		assertEquals("c", str);
	}	
	
}
