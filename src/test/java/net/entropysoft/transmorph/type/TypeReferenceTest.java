package net.entropysoft.transmorph.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class TypeReferenceTest {

	@Test
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
	
	@Test
	public void testPrimitiveWrapper() {
		TypeReference<Integer> typeReference = TypeReference.get(Integer.class);
		assertEquals("java.lang.Integer",typeReference.getName());
		assertFalse(typeReference.isPrimitive());
		assertTrue(typeReference.isNumber());
		assertTrue(typeReference.isRawTypeInstance(55));
		assertTrue(typeReference.isType(Integer.class));
		assertTrue(typeReference.hasRawType(Integer.class));
		assertTrue(typeReference.isRawTypeSubOf(Integer.class));
		assertTrue(typeReference.isRawTypeSubOf(Number.class));
	}
	
	@Test
	public void testRawClass() {
		TypeReference<String> typeReference = TypeReference.get(String.class);
		assertEquals("java.lang.String",typeReference.getName());
		assertFalse(typeReference.isPrimitive());
		assertFalse(typeReference.isNumber());
		assertTrue(typeReference.isRawTypeInstance("hello"));
		assertTrue(typeReference.isType(String.class));
		assertTrue(typeReference.hasRawType(String.class));
		assertTrue(typeReference.isRawTypeSubOf(String.class));
	}
	
	@Test
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
		assertEquals(1,typeReference.getTypeArguments().length);
		assertEquals(String.class,typeReference.getTypeArguments()[0].getType());
	}
	
	@Test
	public void testArray() {
		TypeReference<?> typeReference = TypeReference.get(int[][].class);
		assertEquals("[[I",typeReference.getName());
		assertFalse(typeReference.isPrimitive());
		assertFalse(typeReference.isNumber());
		assertTrue(typeReference.isRawTypeInstance(new int[][] { { 1, 2 }} ));
		assertTrue(typeReference.isType(int[][].class));
		assertTrue(typeReference.hasRawType(int[][].class));
		assertTrue(typeReference.isRawTypeSubOf(int[][].class));
		assertEquals(int[].class, typeReference.getArrayComponentType().getType());
		assertEquals(int.class, typeReference.getArrayElementType().getType());
		assertEquals(2, typeReference.getArrayNumDimensions());
	}
	
	@Test
	public void testGenericArray() {
		TypeReference<ArrayList<String>[]> typeReference = new TypeReference<ArrayList<String>[]>() {
		};
		assertEquals("java.util.ArrayList<java.lang.String>[]",typeReference.getName());
		assertFalse(typeReference.isPrimitive());
		assertFalse(typeReference.isNumber());
		assertTrue(typeReference.isRawTypeInstance(new ArrayList[0]));
		assertEquals(new TypeReference<ArrayList<String>>() {}, typeReference.getArrayComponentType());
		assertEquals(new TypeReference<ArrayList<String>>() {}, typeReference.getArrayElementType());
		assertEquals(1, typeReference.getArrayNumDimensions());
	}
	
	@Test
	public void testUnboundedWildcardType() {
		TypeReference<ArrayList<?>> typeReference1 = new TypeReference<ArrayList<?>>() {
		};
		TypeReference<?> typeReference = typeReference1.getTypeArguments()[0];
		assertEquals(Object.class, typeReference.getRawType());
		assertTrue(typeReference.hasRawType(Object.class));
	}
	
	@Test
	public void testUpperboundWildcardType() {
		TypeReference<ArrayList<? extends Number>> typeReference1 = new TypeReference<ArrayList<? extends Number>>() {
		};
		TypeReference<?> typeReference = typeReference1.getTypeArguments()[0];
		assertEquals(Number.class, typeReference.getRawType());
		assertTrue(typeReference.hasRawType(Number.class));
	}

	@Test
	public void testLowerboundWildcardType() {
		TypeReference<ArrayList<? super Number>> typeReference1 = new TypeReference<ArrayList<? super Number>>() {
		};
		TypeReference<?> typeReference = typeReference1.getTypeArguments()[0];
		assertEquals(Object.class, typeReference.getRawType());
		assertTrue(typeReference.hasRawType(Object.class));
	}
	
	
}
