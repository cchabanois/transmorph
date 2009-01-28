package net.entropysoft.transmorph.type;

import junit.framework.TestCase;

public class PrimitiveTypeTest extends TestCase {

	public void testPrimitiveType() throws Exception {
		TypeFactory typeFactory = new TypeFactory(PrimitiveTypeTest.class.getClassLoader());
		PrimitiveType primitiveType = (PrimitiveType)typeFactory.getType(Byte.TYPE);
		assertTrue(primitiveType.isByte());
		assertFalse(primitiveType.isInstance(null));
	}
	
}
