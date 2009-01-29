package net.entropysoft.transmorph;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.TestConverters;
import net.entropysoft.transmorph.modifiers.IModifier;
import net.entropysoft.transmorph.modifiers.TrimString;

public class ConverterTest extends TestCase {

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

	public void testConverterWithContext() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		ConversionContext context = new ConversionContext();

		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);

		// we use the same ConversionContext. This implies that the original
		// objects to convert must not change
		// until conversion is not finished

		String[] arrayOfStrings = (String[]) converter.convert(context, list,
				"[Ljava.lang.String;");

		String[] arrayOfStrings2 = (String[]) converter.convert(context, list,
				"[Ljava.lang.String;");
		assertTrue(arrayOfStrings == arrayOfStrings2);
	}


}
