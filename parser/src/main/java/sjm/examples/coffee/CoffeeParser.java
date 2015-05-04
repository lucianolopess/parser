/*
 * @(#)CoffeeParser.java	 1.0.0
 *
 * Copyright (c) 1999 Steven J. Metsker
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package sjm.examples.coffee;

import sjm.parse.*;
import sjm.parse.tokens.*;

public class CoffeeParser {

	/**
	 * Return a parser that will recognizer the grammar:
	 * 
	 * <bloquequote><pre>
	 * 
	 * 		coffee = name ',' roast ',' country ',' price;
	 * 
	 * </pre></blockquote>
	 * 
	 * This parse creates a <code>Coffee</code> object as an 
	 * assembly's target.
	 * 
	 * @return a parser that will recognize and build a 
	 * <code>Coffee</code> from a textual description
	 */
	public Parser coffee() {
		Symbol comma = new Symbol(',');
		comma.discard();
		Sequence s = new Sequence();
		s.add(name());
		s.add(comma);
		s.add(roast());
		s.add(comma);
		s.add(country());
		s.add(comma);
		s.add(price());
		return s;
	}
	
	/**
	 * Return a parser that will recognize the grammar:
	 * 
	 * 		country = Word;
	 * 
	 * Use CountryAssembler to update the target coffee
	 * object.
	 * 
	 */
	protected Parser country() {
		return new Word().setAssembler(new CountryAssembler());
	}
	
	/**
	 * Return a parser that will recognize the grammar:
	 * 
	 * 		formerName = '(' Word ')'
	 * 
	 * User a FormerNameAssembler to update the target coffee
	 * object.
	 */
	protected Parser formerName() {
		Sequence s = new Sequence();
		s.add(new Symbol('(').discard());
		s.add(new Word().setAssembler(new FormerNameAssembler()));
		s.add(new Symbol(')').discard());
		return s;
	}
	
	/**
	 * Return a parser that will recognize the grammar:
	 * 
	 * 		name = Word (formerName | empty);
	 * 
	 * User a NameAssembler to update the target coffee object
	 * with the recognized Word; formerName also uses an assembler;
	 */
	protected Parser name() {
		Sequence s = new Sequence();
		s.add(new Word().setAssembler(new NameAssembler()));
		Alternation a = new Alternation();
		a.add(formerName());
		a.add(new Empty());
		s.add(a);
		return s;
	}
	
	/**
	 * Return a parser that will recognize the sequence:
	 * 
	 * 		orFrench = '/' "french";
	 * 
	 * Use an AlsoFrenchAssembler to update the target coffee
	 * object
	 */
	protected Parser orFrench() {
		Sequence s = new Sequence();
		s.add(new Symbol('/').discard());
		s.add(new CaselessLiteral("french").discard());
		s.setAssembler(new AlsoFrenchAssembler());
		return s;
	}
	
	/** 
	 * Return a parser that will recognize the sequence:
	 * 
	 * price = Num;
	 * 
	 * Use a PriceAssembler to update the target coffee object.
	 */
	protected Parser price() {
		return new Num().setAssembler(new PriceAssembler());
	}
	
	/*
	 * Return a parser that will recognize the grammar:
	 * 
	 *     roast = Word (orFrench | Empty);
	 *
	 * Use a RoastAssembler to update the target coffee object 
	 * with the recognized Word; orFrench also uses an 
	 * assembler.
	 */
	protected Parser roast() {
		Sequence s = new Sequence();
		s.add(new Word().setAssembler(new RoastAssembler()));
		Alternation a = new Alternation();
		a.add(orFrench());
		a.add(new Empty());
		s.add(a);
		return s;
	}
	
	/**
	 * Return the primary parser for this class -- coffee().
	 *
	 * @return the primary parser for this class -- coffee()
	 */
	public static Parser start() {
		return new CoffeeParser().coffee();
	}
	
	/**
	 * Returns a tokenizer that allows spaces to appear inside
	 * the "words" that identify a coffee's name.
	 * 
	 * @return a tokenizer that allows spaces to appear inside
	 * the "words" that identify a coffee's name.
	 */
	public static Tokenizer tokenizer() {
		Tokenizer t = new Tokenizer();
		t.wordState().setWordChars(' ', ' ', true);
		return t;
	}
}
