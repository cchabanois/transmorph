package net.entropysoft.transmorph.type;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TypeUtilsTest {

	@Test
	public void testIsAssignableFromClass() {
		TypeReference<List> to = TypeReference.get(List.class);
		TypeReference<ArrayList> from = TypeReference.get(ArrayList.class);
		assertTrue(TypeUtils.isAssignableFrom(from, to));
	}
	
	@Test
	public void testIsAssignableFromParameterizedTypeToUnboundedList() {
		TypeReference<List<?>> to = new TypeReference<List<?>>() {
		};
		TypeReference<ArrayList<Number>> from = new TypeReference<ArrayList<Number>>() {
		};
		assertTrue(TypeUtils.isAssignableFrom(from, to));
		List<?> sample = new ArrayList<Number>();
	}

	@Test
	public void testIsAssignableFromParameterizedTypeToBoundedList() {
		TypeReference<List<Number>> to = new TypeReference<List<Number>>() {
		};
		TypeReference<ArrayList<Integer>> from = new TypeReference<ArrayList<Integer>>() {
		};
		assertTrue(TypeUtils.isAssignableFrom(from, to));
	}
	
	
}
