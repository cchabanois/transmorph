package net.entropysoft.transmorph.signature.formatter;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.signature.formatter");
		//$JUnit-BEGIN$
		suite.addTestSuite(ClassGetNameSignatureFormatterTest.class);
		suite.addTestSuite(TypeSignatureFormatterTest.class);
		//$JUnit-END$
		return suite;
	}

}
