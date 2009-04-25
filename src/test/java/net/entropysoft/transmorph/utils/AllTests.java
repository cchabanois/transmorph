package net.entropysoft.transmorph.utils;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.utils");
		//$JUnit-BEGIN$
		suite.addTestSuite(NumberInRangeTest.class);
		//$JUnit-END$
		return suite;
	}

}
