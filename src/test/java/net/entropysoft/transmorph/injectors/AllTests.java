package net.entropysoft.transmorph.injectors;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.injectors");
		//$JUnit-BEGIN$
		suite.addTestSuite(BeanToBeanInjectorTest.class);
		suite.addTestSuite(MapToBeanTest.class);
		//$JUnit-END$
		return suite;
	}

}
