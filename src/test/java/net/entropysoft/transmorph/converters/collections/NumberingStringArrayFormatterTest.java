package net.entropysoft.transmorph.converters.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberingStringArrayFormatterTest {

	@Test
	public void testNumberingStringArrayFormatter() throws Exception {
		NumberingStringArrayFormatter formatter = new NumberingStringArrayFormatter();
		
		String result = formatter.format(new String[] { "first", "second", "third"});
		assertEquals("[0]=first\n[1]=second\n[2]=third", result);
	}
	
}
