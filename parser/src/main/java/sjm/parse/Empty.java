/*
 * @(#)Empty.java	 1.0.0
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

public class Empty extends Parser {

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
		pv.visitEmpty(this, visited);
	}

	/**
	 * Given a set of assemblies, this method returns the set as a successful
	 * match.
	 * 
	 * @return the input set of states
	 * 
	 * @param Vector
	 *            a vector of assemblies to match against
	 * 
	 */
	public Vector match(Vector in) {
		return elementClone(in);
	}

	/*
	 * There really is no way to expand an empty parser, so return an empty
	 * vector.
	 */
	protected Vector randomExpansion(int maxDepth, int depth) {
		return new Vector();
	}

	/*
	 * Returns a textual description of this parser.
	 */
	protected String unvisitedString(Vector visited) {
		return " empty ";
	}
}