package net.entropysoft.transmorph.signature.parser;

import junit.framework.TestCase;

public class JavaTypeSignatureLexerTest extends TestCase {

	public void testJavaTypeSignatureLexer() throws Exception {
		JavaSyntaxTypeSignatureLexer lexer = new JavaSyntaxTypeSignatureLexer("java.util.Map<java.lang.String, java.lang.Integer>");
		JavaSyntaxTypeSignatureLexer.Token token;

		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("java", token.text);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.PACKAGE_SEPARATOR, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("util", token.text);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.PACKAGE_SEPARATOR, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("Map", token.text);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.TYPE_ARG_BEGIN, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("java", token.text);

		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.PACKAGE_SEPARATOR, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("lang", token.text);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.PACKAGE_SEPARATOR, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("String", token.text);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.TYPE_ARG_SEPARATOR, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("java", token.text);

		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.PACKAGE_SEPARATOR, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("lang", token.text);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.PACKAGE_SEPARATOR, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.JAVA_ID, token.tokenType);
		assertEquals("Integer", token.text);

		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.TYPE_ARG_END, token.tokenType);
		
		token = lexer.nextToken();
		assertEquals(JavaSyntaxTypeSignatureLexer.TokenType.END_OF_TOKENS, token.tokenType);
	}
	
}
