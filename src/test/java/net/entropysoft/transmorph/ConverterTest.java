package net.entropysoft.transmorph;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.modifiers.IModifier;
import net.entropysoft.transmorph.modifiers.TrimString;
import net.entropysoft.transmorph.signature.parser.ClassFileTypeSignatureParser;

public class ConverterTest extends TestCase {

	public void testTrimString() throws Exception {
		IdentityConverter identityConverter = new IdentityConverter();
		identityConverter.setModifiers(new IModifier[] { new TrimString() });
		Transmorph transmorph = new Transmorph(ConverterTest.class
				.getClassLoader(), identityConverter);
		String converted = (String) transmorph
				.convert(
						"    This is a string with leading and trailing white spaces    ",
						String.class);
		assertEquals("This is a string with leading and trailing white spaces",
				converted);
	}

	public void testConverterWithContext() throws Exception {
		Transmorph transmorph = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());
		transmorph.setTypeSignatureParser(new ClassFileTypeSignatureParser(false));
		ConversionContext context = new ConversionContext();

		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);

		// we use the same ConversionContext. This implies that the original
		// objects to convert must not change
		// until conversion is not finished

		String[] arrayOfStrings = (String[]) transmorph.convert(context, list,
				"[Ljava.lang.String;");

		String[] arrayOfStrings2 = (String[]) transmorph.convert(context, list,
				"[Ljava.lang.String;");
		assertTrue(arrayOfStrings == arrayOfStrings2);
	}


}
