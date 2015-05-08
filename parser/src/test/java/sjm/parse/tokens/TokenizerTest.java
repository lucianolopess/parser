package sjm.parse.tokens;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TokenizerTest {

	@Test
	public void testNextToken() throws IOException {
		Tokenizer t = new Tokenizer("A65 B66");
		assertEquals("nextoken()", "A65", t.nextToken().toString());
		assertEquals("nextoken()", "B66", t.nextToken().toString());
		assertEquals("nextoken()", "EOF", t.nextToken().toString());
	}
	
}
