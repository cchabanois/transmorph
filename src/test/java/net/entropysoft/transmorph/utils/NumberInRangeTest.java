package net.entropysoft.transmorph.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class NumberInRangeTest {

	@Test
	public void testIsInByteRange() {
		assertTrue(NumberInRange.isInByteRange(0));
		assertTrue(NumberInRange.isInByteRange(-128));
		assertTrue(NumberInRange.isInByteRange(127));
		assertFalse(NumberInRange.isInByteRange(128));
		assertFalse(NumberInRange.isInByteRange(-129));
		assertTrue(NumberInRange.isInByteRange(126.55));
		assertTrue(NumberInRange.isInByteRange(127.01));
		assertFalse(NumberInRange.isInByteRange(128.55));
		assertFalse(NumberInRange.isInByteRange(Float.NaN));
		assertFalse(NumberInRange.isInByteRange(Float.POSITIVE_INFINITY));
	}

	@Test
	public void testIsInShortRange() {
		assertTrue(NumberInRange.isInShortRange(0));
		assertTrue(NumberInRange.isInShortRange(-32768));
		assertTrue(NumberInRange.isInShortRange(32767));
		assertFalse(NumberInRange.isInShortRange(32768));
		assertFalse(NumberInRange.isInShortRange(-32769));
		assertTrue(NumberInRange.isInShortRange(32767.55));
		assertFalse(NumberInRange.isInShortRange(32768.88));
		assertFalse(NumberInRange.isInShortRange(Float.NaN));
		assertFalse(NumberInRange.isInShortRange(Float.POSITIVE_INFINITY));
	}

	@Test
	public void testIsInIntegerRange() {
		assertTrue(NumberInRange.isInIntegerRange(0));
		assertTrue(NumberInRange.isInIntegerRange(Integer.MIN_VALUE));
		assertTrue(NumberInRange.isInIntegerRange(Integer.MAX_VALUE));
		assertFalse(NumberInRange.isInIntegerRange((long)Integer.MAX_VALUE+1));
		assertFalse(NumberInRange.isInIntegerRange((long)Integer.MIN_VALUE-1));
		assertTrue(NumberInRange.isInIntegerRange((double)Integer.MAX_VALUE+0.55));
		assertFalse(NumberInRange.isInIntegerRange(Float.NaN));
		assertFalse(NumberInRange.isInIntegerRange(Float.POSITIVE_INFINITY));
	}
	
	@Test
	public void testIsInLongRange() {
		assertTrue(NumberInRange.isInLongRange(0));
		assertTrue(NumberInRange.isInLongRange(Long.MIN_VALUE));
		assertTrue(NumberInRange.isInLongRange(Long.MAX_VALUE));
		assertFalse(NumberInRange.isInLongRange(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.valueOf(1))));
		assertFalse(NumberInRange.isInLongRange(BigInteger.valueOf(Long.MIN_VALUE).add(BigInteger.valueOf(-1))));
		assertTrue(NumberInRange.isInLongRange(BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.valueOf(0.55))));
		assertFalse(NumberInRange.isInLongRange(Float.NaN));
		assertFalse(NumberInRange.isInLongRange(Float.POSITIVE_INFINITY));
	}

	
}
