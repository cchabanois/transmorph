package net.entropysoft.transmorph.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class NumberUtilsTest {

	@Test
	public void testCommonNumberClass() {
		assertEquals(Byte.class, NumberUtils.getCommonNumberClass(Byte.class,
				Byte.class));
		assertEquals(Long.class, NumberUtils.getCommonNumberClass(Byte.class,
				Long.class));
		assertEquals(Long.class, NumberUtils.getCommonNumberClass(Long.class,
				Byte.class));
		assertEquals(BigInteger.class, NumberUtils.getCommonNumberClass(Byte.class,
				BigInteger.class));
		assertEquals(Double.class, NumberUtils.getCommonNumberClass(Double.class,
				Double.class));
		assertEquals(Double.class, NumberUtils.getCommonNumberClass(Float.class,
				Double.class));
		assertEquals(Double.class, NumberUtils.getCommonNumberClass(Double.class,
				Float.class));
		assertEquals(BigDecimal.class, NumberUtils.getCommonNumberClass(Double.class,
				Byte.class));
		assertEquals(BigDecimal.class, NumberUtils.getCommonNumberClass(BigInteger.class,
				Float.class));
		assertEquals(BigDecimal.class, NumberUtils.getCommonNumberClass(BigInteger.class,
				BigDecimal.class));
	}

}
