package net.entropysoft.transmorph.type;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.type");
		//$JUnit-BEGIN$
		suite.addTestSuite(ClassTypeTest.class);
		suite.addTestSuite(PrimitiveTypeTest.class);
		//$JUnit-END$
		return suite;
	}

}