package net.entropysoft.transmorph;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.entropysoft.transmorph.converters.ArrayToArrayTest;
import net.entropysoft.transmorph.converters.ArrayToListTest;
import net.entropysoft.transmorph.converters.IdentityConverterTest;
import net.entropysoft.transmorph.converters.MapToMapTest;
import net.entropysoft.transmorph.converters.NumberToNumberTest;
import net.entropysoft.transmorph.signature.ClassFactoryTest;
import net.entropysoft.transmorph.signature.JavaTypeToTypeSignatureTest;
import net.entropysoft.transmorph.signature.TypeSignatureFactoryTest;
import net.entropysoft.transmorph.signature.TypeSignatureParserTest;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for net.entropysoft.transformers");
        suite.addTestSuite(ClassFactoryTest.class);
        suite.addTestSuite(JavaTypeToTypeSignatureTest.class);
        suite.addTestSuite(TypeSignatureFactoryTest.class);
        suite.addTestSuite(TypeSignatureParserTest.class);
        suite.addTestSuite(ArrayToArrayTest.class);
        suite.addTestSuite(ArrayToListTest.class);
        suite.addTestSuite(IdentityConverterTest.class);
        suite.addTestSuite(MapToMapTest.class);
        suite.addTestSuite(NumberToNumberTest.class);
        suite.addTestSuite(ConverterTest.class);
        return suite;
    }

}
