package net.entropysoft.transmorph.signature.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { ClassGetNameTypeSignatureParserTest.class,
		JavaSyntaxTypeSignatureParserTest.class,
		JavaTypeSignatureLexerTest.class,
		ClassFileTypeSignatureParserTest.class })
public class AllTests {

}
