package net.entropysoft.transmorph;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.entropysoft.transmorph.context.UsedConvertersTest;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for net.entropysoft.transformers");
        suite.addTestSuite(UsedConvertersTest.class);
        suite.addTest(net.entropysoft.transmorph.signature.AllTests.suite());
        suite.addTest(net.entropysoft.transmorph.type.AllTests.suite());
        suite.addTest(net.entropysoft.transmorph.utils.AllTests.suite());
        suite.addTest(net.entropysoft.transmorph.converters.AllTests.suite());
        suite.addTestSuite(TransmorphTest.class);
        return suite;
    }

}
