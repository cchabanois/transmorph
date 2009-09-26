package net.entropysoft.transmorph.converters;

import static org.junit.Assert.assertEquals;
import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class ImmutableIdentityConverterTest {

	@Test
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

	@Test
	public void testImmutableToObject() throws Exception {
		Transmorph converter = new Transmorph(new ImmutableIdentityConverter());
		assertEquals(45, converter.convert(45, Object.class));
		assertEquals(45, converter.convert(45, Number.class));
	}
	
}
