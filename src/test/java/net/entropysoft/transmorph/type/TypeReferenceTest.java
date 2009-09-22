package net.entropysoft.transmorph.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

public class TypeReferenceTest extends TestCase {

	public void testPrimitive() {
		TypeReference<?> typeReference = TypeReference.get(Integer.TYPE);
		assertEquals("int",typeReference.getName());
		assertTrue(typeReference.isPrimitive());
		assertTrue(typeReference.isNumber());
		assertFalse(typeReference.isRawTypeInstance(55));
		assertTrue(typeReference.isType(Integer.TYPE));
		assertTrue(typeReference.hasRawType(Integer.TYPE));
		assertTrue(typeReference.isRawTypeSubOf(Integer.TYPE));
		assertFalse(typeReference.isRawTypeSubOf(Number.class));
	}
	
	public void testPrimitiveWrapper() {
		TypeReference<?> typeReference = TypeReference.get(Integer.class);
		assertEquals("java.lang.Integer",typeReference.getName());
		assertFalse(typeReference.isPrimitive());
		assertTrue(typeReference.isNumber());
		assertTrue(typeReference.isRawTypeInstance(55));
		assertTrue(typeReference.isType(Integer.class));
		assertTrue(typeReference.hasRawType(Integer.class));
		assertTrue(typeReference.isRawTypeSubOf(Integer.class));
		assertTrue(typeReference.isRawTypeSubOf(Number.class));
	}
	
	public void testRawClass() {
		TypeReference<?> typeReference = TypeReference.get(String.class);
		assertEquals("java.lang.String",typeReference.getName());
		assertFalse(typeReference.isPrimitive());
		assertFalse(typeReference.isNumber());
		assertTrue(typeReference.isRawTypeInstance("hello"));
		assertTrue(typeReference.isType(String.class));
		assertTrue(typeReference.hasRawType(String.class));
		assertTrue(typeReference.isRawTypeSubOf(String.class));
	}
	
	public void testParameterizedClass() {
		TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
		};
		assertEquals("java.util.List<java.lang.String>",typeReference.getName());
		assertFalse(typeReference.isPrimitive());
		assertFalse(typeReference.isNumber());
		ArrayList<String> arrayOfStrings = new ArrayList<String>();
		assertTrue(typeReference.isRawTypeInstance(arrayOfStrings));
		
		ArrayList<Integer> arrayOfIntegers = new ArrayList<Integer>();
		assertTrue(typeReference.isRawTypeInstance(arrayOfIntegers));
		
		assertTrue(typeReference.isType(typeReference.getType()));
		assertTrue(typeReference.hasRawType(List.class));
		assertTrue(typeReference.isRawTypeSubOf(List.class));
		assertTrue(typeReference.isRawTypeSubOf(Collection.class));
	}
	
}
