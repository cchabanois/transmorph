package net.entropysoft.transmorph;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.entropysoft.transmorph.signature.ClassFactoryTest;
import net.entropysoft.transmorph.signature.JavaTypeToTypeSignatureTest;
import net.entropysoft.transmorph.signature.TypeSignatureFactoryTest;
import net.entropysoft.transmorph.signature.TypeSignatureParserTest;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for net.entropysoft.transformers");
        suite.addTest(net.entropysoft.transmorph.signature.AllTests.suite());
        suite.addTest(net.entropysoft.transmorph.type.AllTests.suite());
        suite.addTest(net.entropysoft.transmorph.converters.AllTests.suite());
        suite.addTest(net.entropysoft.transmorph.converters.beans.AllTests.suite());
        suite.addTestSuite(ConverterTest.class);
        return suite;
    }

}
