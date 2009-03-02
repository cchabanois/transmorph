package net.entropysoft.transmorph.signature.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.entropysoft.transmorph.signature.ArrayTypeSignature;
import net.entropysoft.transmorph.signature.ClassTypeSignature;
import net.entropysoft.transmorph.signature.FieldTypeSignature;
import net.entropysoft.transmorph.signature.PrimitiveTypeSignature;
import net.entropysoft.transmorph.signature.TypeArgSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.parser.JavaTypeSignatureLexer.Token;
import net.entropysoft.transmorph.signature.parser.JavaTypeSignatureLexer.TokenType;

/**
 * Parser for java type signatures
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class JavaTypeSignatureParser implements ITypeSignatureParser {
	private JavaTypeSignatureLexer lexer;
	private boolean acceptGenerics = true;
	private Map<String, String> importedClasses = new HashMap<String, String>();
	
	public JavaTypeSignatureParser(String typeSignature) {
		setTypeSignature(typeSignature);
	}	
	
	public TypeSignature parseTypeSignature() throws InvalidSignatureException {
		TypeSignature typeSignature = parseJavaTypeSignature();
		lexer.nextToken(TokenType.END_OF_TOKENS);
		return typeSignature;
	}

	public void setTypeSignature(String signature) {
		lexer = new JavaTypeSignatureLexer(signature);
	}

	public boolean isAcceptGenerics() {
		return acceptGenerics;
	}

	public void setAcceptGenerics(boolean acceptGenerics) {
		this.acceptGenerics = acceptGenerics;
	}

	public void importClass(String fullyQualifiedClassName) {
		String shortName = getShortName(fullyQualifiedClassName);
		importedClasses.put(shortName, fullyQualifiedClassName);
	}
	
	private String getShortName(String fullyQualifiedName) {
		return fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".")+1);
	}
	
	private String getFullyQualifiedName(String shortName) {
		String fullyQualifiedName = importedClasses.get(shortName);
		if (fullyQualifiedName != null) {
			return fullyQualifiedName;
		} else {
			return shortName;
		}
	}
	
	public TypeSignature parseJavaTypeSignature() {
		TypeSignature typeSignature = parsePrimitiveTypeSignature();
		if (typeSignature == null) {
			typeSignature = parseClassTypeSignature();
		}
		
		Token token =  lexer.peekToken(0);
		while (token.tokenType == TokenType.ARRAY) {
			typeSignature = new ArrayTypeSignature(typeSignature);
			lexer.nextToken();
			token =  lexer.peekToken(0);
		}
		return typeSignature;
	}	
	
	public FieldTypeSignature parseFieldTypeSignature() {
		TypeSignature typeSignature = parseJavaTypeSignature();
		if (!(typeSignature instanceof FieldTypeSignature)) {
			throw new InvalidSignatureException("Invalid signature", lexer.peekToken(0).tokenStart);
		}
		return (FieldTypeSignature)typeSignature;
	}
	
	private PrimitiveTypeSignature parsePrimitiveTypeSignature() {
		Token token = lexer.peekToken(0);
		Character primitiveChar = PrimitiveTypeUtils.getChar(token.text);
		if (primitiveChar != null) {
			Token token1 = lexer.peekToken(1);
			if (token1.tokenType == TokenType.END_OF_TOKENS || token1.tokenType == TokenType.ARRAY) {
				lexer.nextToken();
				return new PrimitiveTypeSignature(primitiveChar);
			}
		}
		return null;
	}

	private ClassTypeSignature parseClassTypeSignature() {
		String outerClassName = parseOuterClassName();

		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[0];
		Token token = lexer.peekToken(0);
		if (acceptGenerics && token.tokenType == TokenType.TYPE_ARG_BEGIN) {
			typeArgSignatures = parseTypeArgs();
		}
		ClassTypeSignature classTypeSignature = new ClassTypeSignature(
				outerClassName, typeArgSignatures, null);
		token = lexer.peekToken(0);
		if (token.tokenType == TokenType.INNER_CLASS_PREFIX) {
			classTypeSignature = parseInnerClasses(classTypeSignature);
		}
		return classTypeSignature;		
	}
	
	/**
	 * parse the outer class name
	 * 
	 * @return
	 */
	private String parseOuterClassName() {
		StringBuilder sb = new StringBuilder();
		sb.append(lexer.nextToken(TokenType.JAVA_ID).text);
		Token token = lexer.peekToken(0);
		boolean isFullyQualifiedName = false;
		while (token.tokenType == TokenType.PACKAGE_SEPARATOR) {
			isFullyQualifiedName = true;
			token = lexer.nextToken();
			sb.append('.');
			token = lexer.nextToken(TokenType.JAVA_ID);
			sb.append(token.text);
			token = lexer.peekToken(0);
		}
		if (!isFullyQualifiedName) {
			return getFullyQualifiedName(sb.toString());
		} else {
			return sb.toString();
		}
	}	
	
	private TypeArgSignature[] parseTypeArgs() {
		lexer.nextToken(TokenType.TYPE_ARG_BEGIN);
		List<TypeArgSignature> typeArgSignatures = new ArrayList<TypeArgSignature>();
		typeArgSignatures.add(parseTypeArg());

		Token token = lexer.peekToken(0);
		while (token.tokenType == TokenType.TYPE_ARG_SEPARATOR) {
			lexer.nextToken();
			typeArgSignatures.add(parseTypeArg());
			token = lexer.peekToken(0);
		}
		lexer.nextToken(TokenType.TYPE_ARG_END);
		return typeArgSignatures.toArray(new TypeArgSignature[typeArgSignatures
				.size()]);
	}	
	
	
	private TypeArgSignature parseTypeArg() {
		Token token = lexer.peekToken(0);
		if (token.tokenType == TokenType.TYPE_ARG_QUESTION_MARK) {
			lexer.nextToken();
			token = lexer.peekToken(0);
			if (token.tokenType == TokenType.JAVA_ID && token.text.equals("extends")) {
				lexer.nextToken();
				return new TypeArgSignature(
						TypeArgSignature.UPPERBOUND_WILDCARD,
						parseFieldTypeSignature());
			} else if (token.tokenType == TokenType.JAVA_ID && token.text.equals("super")) {
				lexer.nextToken();
				return new TypeArgSignature(
						TypeArgSignature.LOWERBOUND_WILDCARD,
						parseFieldTypeSignature());
			} else {
				return new TypeArgSignature(
						TypeArgSignature.UNBOUNDED_WILDCARD, null);
			}
		} else {
			return new TypeArgSignature(TypeArgSignature.NO_WILDCARD,
					parseFieldTypeSignature());
		}
	}

	private ClassTypeSignature parseInnerClass(
			ClassTypeSignature ownerClassTypeSignature) {
		lexer.nextToken(TokenType.INNER_CLASS_PREFIX);
		
		Token token = lexer.nextToken(TokenType.JAVA_ID); 
		String id = token.text;
		token = lexer.peekToken(0);
		TypeArgSignature[] typeArgSignatures = new TypeArgSignature[0];
		if (acceptGenerics && token.tokenType == TokenType.TYPE_ARG_BEGIN) {
			typeArgSignatures = parseTypeArgs();
		}
		return new ClassTypeSignature(id, typeArgSignatures,
				ownerClassTypeSignature);
	}

	private ClassTypeSignature parseInnerClasses(
			ClassTypeSignature ownerClassTypeSignature) {
		ClassTypeSignature classTypeSignature = parseInnerClass(ownerClassTypeSignature);
		while (true) {
			Token token = lexer.peekToken(0);
			if (token.tokenType != TokenType.INNER_CLASS_PREFIX) {
				return classTypeSignature;
			}
			classTypeSignature = parseInnerClass(classTypeSignature);
		}
	}	
	
	
}
