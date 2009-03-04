package net.entropysoft.transmorph.converters;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

public class ImmutableIdentityConverterTest extends TestCase {

	public void testPrimitiveToWrapper() throws Exception {
		Transmorph converter = new Transmorph(IdentityConverterTest.class
				.getClassLoader(), new DefaultConverters());
		ConversionContext conversionContext = new ConversionContext();
		conversionContext.setStoreUsedConverters(true);
		boolean[] booleans = new boolean[] { true, false };
		Boolean[] booleanWrappers = (Boolean[]) converter.convert(
				conversionContext, booleans, Boolean[].class);
		assertEquals(Boolean.TRUE, booleanWrappers[0]);
		assertEquals(Boolean.FALSE, booleanWrappers[1]);
		assertEquals(ImmutableIdentityConverter.class, conversionContext
				.getUsedConverters().getUsedConverters()[0].getConverter()
				.getClass());
	}

}
