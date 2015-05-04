/*
 * @(#)Alternation.java	 1.0.0
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

public class Alternation extends CollectionParser {

	/**
	 * Constructs a nameless alternation.
	 */
	public Alternation() {
	}

	/**
	 * Constructs an alternation with the given name.
	 * 
	 * @param name
	 *            a name to be known by
	 */
	public Alternation(String name) {
		super(name);
	}

	/**
	 * A convenient way to construct a CollectionParser with the given parser.
	 */
	public Alternation(Parser p) {
		super(p);
	}

	/**
	 * A convenient way to construct a CollectionParser with the given parsers.
	 */
	public Alternation(Parser p1, Parser p2) {
		super(p1, p2);
	}

	/**
	 * A convenient way to construct a CollectionParser with the given parsers.
	 */
	public Alternation(Parser p1, Parser p2, Parser p3) {
		super(p1, p2, p3);
	}

	/**
	 * A convenient way to construct a CollectionParser with the given parsers.
	 */
	public Alternation(Parser p1, Parser p2, Parser p3, Parser p4) {
		super(p1, p2, p3, p4);
	}

	/**
	 * Accept a "visitor" and a collection of previously visited parsers.
	 * 
	 * @param ParserVisitor
	 *            the visitor to accept
	 * 
	 * @param Vector
	 *            a collection of previously visited parsers
	 */
	public void accept(ParserVisitor pv, Vector visited) {
		pv.visitAlternation(this, visited);
	}

	/**
	 * Given a set of assemblies, this method matches this alternation against
	 * all of them, and returns a new set of the assemblies that result from the
	 * matches.
	 * 
	 * @return a Vector of assemblies that result from matching against a
	 *         beginning set of assemblies
	 * 
	 * @param Vector
	 *            a vector of assemblies to match against
	 * 
	 */
	public Vector match(Vector in) {
		Vector out = new Vector();
		Enumeration e = subparsers.elements();
		while (e.hasMoreElements()) {
			Parser p = (Parser) e.nextElement();
			add(out, p.matchAndAssemble(in));
		}
		return out;
	}

	/*
	 * Create a random collection of elements that correspond to this
	 * alternation.
	 */
	protected Vector randomExpansion(int maxDepth, int depth) {
		if (depth >= maxDepth) {
			return randomSettle(maxDepth, depth);
		}
		double n = (double) subparsers.size();
		int i = (int) (n * Math.random());
		Parser j = (Parser) subparsers.elementAt(i);
		return j.randomExpansion(maxDepth, depth++);
	}

	/*
	 * This method is similar to randomExpansion, but it will pick a terminal if
	 * one is available.
	 */
	protected Vector randomSettle(int maxDepth, int depth) {

		// which alternatives are terminals?

		Vector terms = new Vector();
		Enumeration e = subparsers.elements();
		while (e.hasMoreElements()) {
			Parser j = (Parser) e.nextElement();
			if (j instanceof Terminal) {
				terms.addElement(j);
			}
		}

		// pick one of the terminals or, if there are no
		// terminals, pick any subparser

		Vector which = terms;
		if (terms.isEmpty()) {
			which = subparsers;
		}

		double n = (double) which.size();
		int i = (int) (n * Math.random());
		Parser p = (Parser) which.elementAt(i);
		return p.randomExpansion(maxDepth, depth++);
	}

	/*
	 * Returns the string to show between the parsers this parser is an
	 * alternation of.
	 */
	protected String toStringSeparator() {
		return "|";
	}

}
