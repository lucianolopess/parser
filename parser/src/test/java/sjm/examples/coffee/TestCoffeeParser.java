package sjm.examples.coffee;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import sjm.parse.Assembly;
import sjm.parse.Parser;
import sjm.parse.tokens.TokenAssembly;
import sjm.parse.tokens.Tokenizer;

public class TestCoffeeParser {

	private String coffeelinha = "Brimful, Regular, Kenya, 6.95";
	private String coffeelinhaespacos = "Fragrant Delicto, Regular/French, Peru, 9.95";
	
	@Test
	public void testTokenizer() {
		Tokenizer t  = CoffeeParser.tokenizer();
		assertNotNull(t);
	}
	
	@Test
	public void testGetTarget() throws IOException {
		Reader reader = new StringReader(coffeelinha);
		BufferedReader r = new BufferedReader(reader);

		Tokenizer t = CoffeeParser.tokenizer();
		Parser p = CoffeeParser.start();
		while (true) {
			String s = r.readLine();
			if (s == null) {
				break;
			}
			t.setString(s);
			Assembly in = new TokenAssembly(t);
			Assembly out = p.bestMatch(in);
			Coffee coffee = (Coffee) out.getTarget();
			assertNotNull(coffee);
		}
	}
	
	@Test
	public void testGetTargetNameWithSpaces() throws IOException {
		Reader reader = new StringReader(coffeelinhaespacos);
		BufferedReader r = new BufferedReader(reader);

		Tokenizer t = CoffeeParser.tokenizer();
		Parser p = CoffeeParser.start();
		while (true) {
			String s = r.readLine();
			if (s == null) {
				break;
			}
			t.setString(s);
			Assembly in = new TokenAssembly(t);
			Assembly out = p.bestMatch(in);
			Coffee coffee = (Coffee) out.getTarget();
			assertNotNull(coffee);
		}
	}
}
