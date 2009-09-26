package net.entropysoft.transmorph;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class DefaultToStringConvertersTest {

	@Test
	public void testDefaultToStringConverters() throws Exception {
		Transmorph transmorph = new Transmorph(new DefaultToStringConverters());
		
		assertEquals("55.63", transmorph.convert(55.63, String.class));
		assertEquals("[55, hello]", transmorph.convert(Arrays.asList(new Object[] { 55, "hello"}) , String.class));
	}
	
}
