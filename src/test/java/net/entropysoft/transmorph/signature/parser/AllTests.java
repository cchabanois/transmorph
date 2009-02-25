package net.entropysoft.transmorph.signature.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.signature.parser");
		//$JUnit-BEGIN$
		suite.addTestSuite(ClassGetNameTypeSignatureParserTest.class);
		suite.addTestSuite(TypeSignatureParserTest.class);
		//$JUnit-END$
		return suite;
	}

}