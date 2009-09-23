package net.entropysoft.transmorph.utils;

import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.TypeReference;

public class TypeReferenceTest extends TestCase {

	public void testTypeReference() {
		TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {
		};
		TypeSignatureFactory typeSignatureFactory = new TypeSignatureFactory();
		assertEquals("Ljava/util/List<Ljava/lang/String;>;",
				typeSignatureFactory.getTypeSignature(typeRef.getType())
						.toString());
	}

	public void testTypeReferenceWithGenericListUpperbound() {
		TypeReference<List<? extends Number>> typeRef = new TypeReference<List<? extends Number>>() {
		};
		TypeSignatureFactory typeSignatureFactory = new TypeSignatureFactory();
		assertEquals("Ljava/util/List<+Ljava/lang/Number;>;",
				typeSignatureFactory.getTypeSignature(typeRef.getType())
						.toString());
	}

}
