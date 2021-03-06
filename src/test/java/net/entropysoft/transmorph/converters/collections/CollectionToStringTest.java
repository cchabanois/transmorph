package net.entropysoft.transmorph.converters.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class CollectionToStringTest {

	@Test
	public void testListToArray() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

		List<Integer> source = new ArrayList<Integer>();
		source.add(30);
		source.add(40);
		source.add(50);

		String str = converter.convert(source, String.class);
		assertNotNull(str);
		assertEquals("[30, 40, 50]", str);
	}	
	
}
