/*
 * @(#)Parser.java	 1.0.0
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
 
package sjm.parse;

import java.util.*;

public abstract class Parser {

	/**
	 * a name do identity this parser
	 */
	protected String name;
	
	/**
	 * an object that will work on an assembly whenever this
	 * parser sucessfully matches against the assembly
	 */
	protected Assembler assembler;
	
	/**
	 * Constructs a nameless parser.
	 */
	public Parser() {
	}
	
	/**
	 * Constructs a parser with the given name.
	 * 
	 * @param String A name to be known by. For parsers
	 * that are deep composites, a simple name identifying
	 * its purpose is useful.
	 */
	public Parser(String name) {
		this.name = name;
	}
	
	/**
	 * Accepts a "visitor" which will perform some operation on 
	 * a parser structure. The book, "Design Patterns", explains
	 * the visitor pattern.
	 * 
	 * @param ParserVisitor the visitor to accept
	 */
	public void accept(ParserVisitor pv) {
		accept(pv, new Vector());
	}

	/**
	 * Accepts a "visitor" along with a collection of previously
	 * visited parsers.
	 * 
	 * @param ParserVisitor the visitor to accept
	 * 
	 * @param Vector a collection of previously visited parsers.
	 */
	public abstract void accept(ParserVisitor pv, Vector vector);
	
	/** 
	 * Adds the elements of one vector to another.
	 * 
	 * @param v1 the vector to add to
	 * 
	 * @param v2 the vector with elements to add
	 */
	public static void add(Vector v1, Vector v2) {
		Enumeration e = v2.elements();
		while (e.hasMoreElements()) {
			v1.addElement(e.nextElement());
		}
	}

	/**
	 * Returns the most-matched assembly in a collection
	 * 
	 * @return the most-matched assembly in a collection
	 * 
	 * @param Vector the collection to look through
	 */
	public Assembly best(Vector v) {
		Assembly best = null;
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			Assembly a = (Assembly) e.nextElement();
			if (!a.hasMoreElements()) {
				return a;
			}
			if (best == null) {
				best = a;
			} else {
				if (a.elementsConsumed() > best.elementsConsumed()) {
					best = a;
				}
			}
		}
		return best;
	}
	
	/**
	 * Returns an assembly with the greatest possible number of
	 * elements consumed by matches of this parser
	 * 
	 * @return an assembly with the greatest possible number of
	 * elements consumed by this parser
	 * 
	 * @param Assembly an assembly to match against
	 */
	public Assembly bestMatch(Assembly a) {
		Vector in = new Vector();
		in.addElement(a);
		Vector out = matchAndAssemble(in);
		return best(out);
	}
	
	/** Returns either null, or a completely matched version of 
	 * the supplied assembly
	 * 
	 * @return either null, o a completely matched version of the supplied
	 * assembly
	 * 
	 * @param Assembly an assembly to match against
	 */
	public Assembly completeMatch(Assembly a) {
		Assembly best = bestMatch(a);
		if (best != null && !best.hasMoreElements()) {
			return best;
		}
		return null;
	}
	
	/**
	 * Create a copy of a vector, cloning each element of the vector
	 * 
	 * @param in the vector to copy
	 * 
	 * @return a copy of the input vector, cloning each element of the 
	 * vector
	 */
	public static Vector elementClone(Vector v) {
		Vector copy = new Vector();
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			Assembly a = (Assembly) e.nextElement();
			copy.addElement(a.clone());
		}
		return copy;
	}
	
	/**
	 * Returns the name of this parser
	 * 
	 * @returns the name of this parser
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Given a set (well, a <code>Vector</code>, really) of 
	 * assemblies, this method matches this parser against
	 * all of them, and returns a new set (also really a 
	 * <code>Vector</code> of the assemblies that result from 
	 * the matches.
	 * <p>
	 * For example, consider matching the regular expression
	 * <code>a*</code> against the string <code>"aaab"</code>.
	 * The initial set of states is <code>{^aaab}</code>, where
	 * the ^ indicates how far along the assembly is. When 
	 * <code>a*</code> matches against this initial state, it 
	 * creates a new set <code>{^aaab, a^aab, aa^ab, aaa^b}</code>.
	 * 
	 * @return a Vector of assemblies that result from 
	 * matching against a beginning set of assemblies
	 * 
	 * @param Vector a vector of assemblies to match against
	 * 
	 */
	public abstract Vector match(Vector in);
	
	/**
	 * Match this parser against an input state, and then 
	 * apply this parser's assembler against the resulting
	 * state.
	 * 
	 * @return a Vector of assemblies that result from matching
	 * against a beginning set of assemblies
	 * 
	 * @param Vector a vector of assemblies to match against
	 */
	public Vector matchAndAssemble(Vector in) {
		Vector out = match(in);
		if (assembler != null) {
			Enumeration e = out.elements();
			while (e.hasMoreElements()) {
				assembler.workOn((Assembly) e.nextElement());
			}
		}
		return out;
	}
	
	/** 
	 * Create a random expansion for this parser, where a 
	 * concatenation of the returned collection will be a 
	 * language element.
	 * 
	 */
	protected abstract Vector randomExpansion(int maxDepth, int depth);
	
	/**
	 * Return a randon element of this parser's language.
	 * 
	 * @return a random element of this parser's language
	 */
	public String randomInput(int maxDept, String separator) {
		StringBuffer buf = new StringBuffer();
		Enumeration e = randomExpansion(maxDept, 0).elements();
		boolean first = true;
		while (e.hasMoreElements()) {
			if (!first) {
				buf.append(separator);
			}
			buf.append(e.nextElement());
			first = false;
		}
		return buf.toString();
	}
	
	/**
	 * Sets the object that will work on an assembly whenever
	 * this parser successfully matches against the assembly.
	 * 
	 * @param Assembler the assembler to apply
	 * 
	 * @return Parser this
	 */
	public Parser setAssembler(Assembler assembler) {
		this.assembler = assembler;
		return this;
	}
	
	/**
	 * Returns a textual description of this parser.
	 * 
	 * @return String a textual description of this parser, taking care
	 * to avoid infinite recursion
	 */
	public String toString() {
		return toString(new Vector());
	}
	
	/**
	 * Returns a textual description of this parser. 
	 * Parsers can be recursive, so when building a 
	 * descriptive string, it is importante to avoid infinitive
	 * recursion by keeping track of the objects already described.
	 * This method keeps an object from printing
	 * twice, and uses <code>unvisitedString</code> which
	 * subclasses must implement.
	 * 
	 * @param Vector a list of objects already printed
	 * 
	 * @return a textual version of this parser,
	 * avoiding recursion
	 * 
	 */
	protected String toString(Vector visited) {
		if (name != null) {
			return name;
		} else if (visited.contains(this)) {
			return "...";
		} else {
			visited.addElement(this);
			return unvisitedString(visited);
		}
	}
	
	/**
	 * Returns a textual description of this string.
	 */
	protected abstract String unvisitedString(Vector visited);
}
