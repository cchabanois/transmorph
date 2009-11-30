package net.entropysoft.transmorph.converters.collections;

import static org.junit.Assert.*;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.ObjectToString;

import org.junit.Test;

public class ArrayToSingleElementTest {

	@Test
	public void testArrayToSingleElement() throws Exception {
		Transmorph transmorph = new Transmorph(new IdentityConverter(),
				new ArrayToSingleElement(),
				new ObjectToString());
		assertEquals(Integer.valueOf(55), transmorph.convert(
				new Integer[] { 55 }, Integer.class));

		assertEquals("55", transmorph.convert(new Integer[] { 55 },
				String.class));
	}

}
