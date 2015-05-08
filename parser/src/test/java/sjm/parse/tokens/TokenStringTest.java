package sjm.parse.tokens;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.Parser;
import sjm.parse.Repetition;

public class TokenStringTest {

	@Test
	public void testNextTokenString() {

		// a repetition of the word counter
		Parser p = new Repetition(getParseCountsWords());
		
		// consume token strings separated by semicolons
		String s = "I came; I saw; I left in peace;";
		Tokenizer t = new Tokenizer(s);
		TokenStringSource tss = new TokenStringSource(t, ";");
		
		// count the words in each token string
		while (tss.hasMoreTokenStrings()) {
			TokenString ts = tss.nextTokenString();
			TokenAssembly ta = new TokenAssembly(ts);
			Assembly a = p.completeMatch(ta);
			System.out.println(ts + " (" + a.pop() + " words)");
		}
	}

	/**
	 * A parser that counts words
	 * 
	 * @return Parser
	 */
	private Parser getParseCountsWords() {
		Parser w = new Word().discard();
		w.setAssembler(new Assembler() {
			
			@Override
			public void workOn(Assembly a) {
				if (a.stackIsEmpty()) {
					a.push(new Integer(1));
				} else {
					Integer i = (Integer) a.pop();
					a.push(new Integer(i.intValue() + 1));
				}
			}
		});
		return w;
	}
	
}
