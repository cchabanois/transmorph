package net.entropysoft.transmorph.converters.collections;

import static org.junit.Assert.*;

import java.util.Arrays;

import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.ObjectToString;

import org.junit.Test;

public class CollectionToSingleElementTest {

	@Test
	public void testCollectionToSingleElement() throws Exception {
		Transmorph transmorph = new Transmorph(new IdentityConverter(),
				new CollectionToSingleElement(), new ObjectToString());
		assertEquals(Integer.valueOf(55), transmorph.convert(Arrays
				.asList(new Integer[] { 55 }), Integer.class));

		assertEquals("55", transmorph.convert(Arrays
				.asList(new Integer[] { 55 }), String.class));
	}

}
