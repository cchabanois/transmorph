package net.entropysoft.transmorph.utils;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class NumberComparatorTest {

	@Test
	public void testNumberComparator() {
		NumberComparator numberComparator = new NumberComparator();
		assertTrue(numberComparator.compare(12, 24) < 0);
		assertTrue(numberComparator.compare((byte) 12, (long) 24) < 0);
		assertTrue(numberComparator.compare((byte) 12, 24.0) < 0);
		assertTrue(numberComparator.compare(25.0, 24.0) > 0);
		assertTrue(numberComparator.compare((double) 25.0, (float) 24.0) > 0);
		assertTrue(numberComparator.compare(new BigDecimal(25.0), (float) 24.0) > 0);
	}

}
