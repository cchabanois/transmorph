package net.entropysoft.transmorph.converters.collections;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.converters.collections");
		//$JUnit-BEGIN$
		suite.addTestSuite(CollectionToCollectionTest.class);
		suite.addTestSuite(MapToMapTest.class);
		suite.addTestSuite(ArrayToArrayTest.class);
		suite.addTestSuite(ArrayToCollectionTest.class);
		suite.addTestSuite(CollectionToArrayTest.class);
		//$JUnit-END$
		return suite;
	}

}
