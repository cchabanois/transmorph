package net.entropysoft.transmorph.signature;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.signature");
		//$JUnit-BEGIN$
		suite.addTestSuite(JavaTypeToTypeSignatureTest.class);
		suite.addTestSuite(ClassFactoryTest.class);
		suite.addTestSuite(TypeSignatureFactoryTest.class);
		//$JUnit-END$
		suite.addTest(net.entropysoft.transmorph.signature.parser.AllTests.suite());
		return suite;
	}

}
