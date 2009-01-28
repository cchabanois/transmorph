package net.entropysoft.transmorph.type;

import junit.framework.TestCase;

public class ClassTypeTest extends TestCase {

	public void testClassType() throws Exception {
		TypeFactory typeFactory = new TypeFactory(PrimitiveTypeTest.class.getClassLoader());
		ClassType classType = (ClassType)typeFactory.getType(Byte.class);
		assertFalse(classType.isInstance(null));
	}
	
}
