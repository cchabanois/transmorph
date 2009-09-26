package net.entropysoft.transmorph.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

public class TypeReferenceTest {

	@Test
	public void testTypeReference() {
		TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {
		};
		TypeSignatureFactory typeSignatureFactory = new TypeSignatureFactory();
		assertEquals("Ljava/util/List<Ljava/lang/String;>;",
				typeSignatureFactory.getTypeSignature(typeRef.getType())
						.toString());
	}

	@Test
	public void testTypeReferenceWithGenericListUpperbound() {
		TypeReference<List<? extends Number>> typeRef = new TypeReference<List<? extends Number>>() {
		};
		TypeSignatureFactory typeSignatureFactory = new TypeSignatureFactory();
		assertEquals("Ljava/util/List<+Ljava/lang/Number;>;",
				typeSignatureFactory.getTypeSignature(typeRef.getType())
						.toString());
	}

}
