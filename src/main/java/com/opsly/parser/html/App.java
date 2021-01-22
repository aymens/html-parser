package com.opsly.parser.html;

import java.net.URL;
import java.nio.charset.Charset;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

public class App {

	public static void main(String[] args) throws Exception {

		String fileURL = getURL(args);
		
		System.out.println("Parse tree of " + fileURL);
		
		CharStream charStream = CharStreams.fromStream(new URL(fileURL).openStream(),
				Charset.defaultCharset());

		// Create an Lexer that receives the char stream
		HTMLLexer lexer = new HTMLLexer(charStream);

		// Create a token stream from the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		tokens.fill();

		// Create a parser that receives the token stream
		HTMLParser parser = new HTMLParser(tokens);

		parser.setBuildParseTree(true);

		parser.setTokenStream(tokens);

		ParserRuleContext tree = parser.htmlDocument();

		Trees.inspect(tree, parser);
	}

	private static String getURL(String[] args) {
		String url = null;
		if(args.length !=1) {
			System.out.println("Wrong arguments number!\n "//
					+ "This program expects one single string argument reprenting a url of a html page.\n"//
					+ "Examples:\n"
					+ "(html document on the internet)\n"
					+ "java -jar path_to_jar/html-parser-jar-with-dependencies.jar \"https://www.wikipedia.org/\"\n"
					+ "(local html file with relative path)\n"
					+ "java -jar path_to_jar/html-parser-jar-with-dependencies.jar \"file:./pom.xml\"");
			System.exit(1);
		} else {
			url = args[0];
		}
		return url;
	}

}
