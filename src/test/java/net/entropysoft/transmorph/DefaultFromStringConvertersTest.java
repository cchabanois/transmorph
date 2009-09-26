package net.entropysoft.transmorph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultFromStringConvertersTest {

	@Test
	public void testDefaultFromStringConverters() throws Exception {
		Transmorph transmorph = new Transmorph(
				new DefaultFromStringConverters());

		assertEquals(52.32, transmorph.convert("52.32", Float.TYPE), 0.001);
		assertEquals(
				DefaultFromStringConvertersTest.class,
				(Class) transmorph
						.convert(
								"net.entropysoft.transmorph.DefaultFromStringConvertersTest",
								Class.class));

	}

}
