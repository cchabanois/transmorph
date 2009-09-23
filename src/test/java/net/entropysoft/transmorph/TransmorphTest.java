package net.entropysoft.transmorph;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.modifiers.IModifier;
import net.entropysoft.transmorph.modifiers.TrimString;
import net.entropysoft.transmorph.type.TypeReference;

public class TransmorphTest extends TestCase {

	public void testTrimString() throws Exception {
		IdentityConverter identityConverter = new IdentityConverter();
		identityConverter.setModifiers(new IModifier[] { new TrimString() });
		Transmorph transmorph = new Transmorph(identityConverter);
		String converted = (String) transmorph
				.convert(
						"    This is a string with leading and trailing white spaces    ",
						String.class);
		assertEquals("This is a string with leading and trailing white spaces",
				converted);
	}

	public void testConverterWithContext() throws Exception {
		Transmorph transmorph = new Transmorph(new DefaultConverters());

		ConversionContext context = new ConversionContext();

		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);

		// we use the same ConversionContext. This implies that the original
		// objects to convert must not change
		// until conversion is not finished
		String[] arrayOfStrings = (String[]) transmorph.convert(context, list,
				String[].class);

		String[] arrayOfStrings2 = (String[]) transmorph.convert(context, list,
				String[].class);
		assertTrue(arrayOfStrings == arrayOfStrings2);
	}

	public void testTransmorphWithJavaTypeSignatureParser() throws Exception {
		Transmorph transmorph = new Transmorph(new DefaultConverters());
		Long longNumber = (Long) transmorph.convert(55, Long.class);
		assertEquals(55, longNumber.longValue());

		TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
		};
		List<String> listOfStrings = (List<String>) transmorph.convert(
				new long[] { 1, 2, 3, 4, 5 }, typeReference);
		assertEquals(5, listOfStrings.size());
		assertEquals("1", listOfStrings.get(0));
		assertEquals("2", listOfStrings.get(1));
		assertEquals("3", listOfStrings.get(2));
		assertEquals("4", listOfStrings.get(3));
		assertEquals("5", listOfStrings.get(4));
	}

	public void testTransmorphWithClassGetNameTypeSignatureParser()
			throws Exception {
		Transmorph transmorph = new Transmorph(new DefaultConverters());
		Long longNumber = (Long) transmorph.convert(55, Long.class);
		assertEquals(55, longNumber.longValue());
	}

}
