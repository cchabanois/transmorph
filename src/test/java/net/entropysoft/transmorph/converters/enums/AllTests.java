package net.entropysoft.transmorph.converters.enums;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.converters.enums");
		//$JUnit-BEGIN$
		suite.addTestSuite(StringToEnumTest.class);
		suite.addTestSuite(EnumToEnumTest.class);
		//$JUnit-END$
		return suite;
	}

}
