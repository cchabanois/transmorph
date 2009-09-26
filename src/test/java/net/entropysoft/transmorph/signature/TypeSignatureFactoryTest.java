package net.entropysoft.transmorph.signature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class TypeSignatureFactoryTest {

	@Test
	public void testClassTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature(String.class);
		assertTrue(typeSignature.isClassType());
		assertEquals("Ljava/lang/String;", typeSignature.getSignature());
	}

	@Test
	public void testArrayTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature((new String[5][5]).getClass());
		assertTrue(typeSignature.isArrayType());
		assertEquals("[[Ljava/lang/String;", typeSignature.getSignature());
	}

	@Test
	public void testPrimitiveTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature(Integer.TYPE);
		assertTrue(typeSignature.isPrimitiveType());
		assertEquals("I", typeSignature.getSignature());
	}

	@Test
	public void testParameterizedTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(
				Map.class, new Class[] { String.class, Integer.class });
		assertTrue(typeSignature.isClassType());
		assertEquals("Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;",
				typeSignature.getSignature());
	}

	@Test
	public void testParseFromString() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature("Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;", true);
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

}
