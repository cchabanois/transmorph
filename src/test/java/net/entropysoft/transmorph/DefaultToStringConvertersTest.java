package net.entropysoft.transmorph;

import java.util.Arrays;

import junit.framework.TestCase;

public class DefaultToStringConvertersTest extends TestCase {

	public void testDefaultToStringConverters() throws Exception {
		Transmorph transmorph = new Transmorph(new DefaultToStringConverters());
		
		assertEquals("55.63", transmorph.convert(55.63, String.class));
		assertEquals("[55, hello]", transmorph.convert(Arrays.asList(new Object[] { 55, "hello"}) , String.class));
	}
	
}
