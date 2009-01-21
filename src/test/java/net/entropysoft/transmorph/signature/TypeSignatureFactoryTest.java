package net.entropysoft.transmorph.signature;

import java.util.Map;

import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;

import junit.framework.TestCase;

public class TypeSignatureFactoryTest extends TestCase {

	public void testClassTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature(String.class);
		assertTrue(typeSignature.isClassType());
		assertEquals("Ljava/lang/String;", typeSignature.getSignature());
	}

	public void testArrayTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature((new String[5][5]).getClass());
		assertTrue(typeSignature.isArrayType());
		assertEquals("[[Ljava/lang/String;", typeSignature.getSignature());
	}

	public void testPrimitiveTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature(Integer.TYPE);
		assertTrue(typeSignature.isPrimitiveType());
		assertEquals("I", typeSignature.getSignature());
	}

	public void testParameterizedTypeSignature() {
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(
				Map.class, new Class[] { String.class, Integer.class });
		assertTrue(typeSignature.isClassType());
		assertEquals("Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;",
				typeSignature.getSignature());
	}

	public void testParseFromString() {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature("Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;", true);
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

}
