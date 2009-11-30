package net.entropysoft.transmorph.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class StaticConverterTest {

	@Test
	public void testStaticConverter() throws Exception {
		StaticConverter staticConverter = new StaticConverter();
		staticConverter.addConverter(Integer.class, String.class,
				new ObjectToString());

		Transmorph converter = new Transmorph(staticConverter);

		String str = converter.convert(55, String.class);
		assertEquals("55", str);

		try {
			converter.convert(new Date(), String.class);
			fail("Should not be able to convert");
		} catch (ConverterException e) {

		}
	}

}
