package net.entropysoft.transmorph.converters.propertyeditors;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.converters.propertyeditors");
		//$JUnit-BEGIN$
		suite.addTestSuite(FromStringUsingPropertyEditorTest.class);
		suite.addTestSuite(ToStringUsingPropertyEditorTest.class);
		//$JUnit-END$
		return suite;
	}

}
