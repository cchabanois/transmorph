package net.entropysoft.transmorph.converters;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

public class ImmutableIdentityConverterTest extends TestCase {

	public void testPrimitiveToWrapper() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());
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

	public void testImmutableToObject() throws Exception {
		Transmorph converter = new Transmorph(new ImmutableIdentityConverter());
		assertEquals(45, converter.convert(45, Object.class));
		assertEquals(45, converter.convert(45, Number.class));
	}
	
}
