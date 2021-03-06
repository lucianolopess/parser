/*
 * @(#)RoastAssembler.java	 1.0.0
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


public class RoastAssembler extends Assembler {
	
	/**
	 * Pop a string, and set the target coffee's roast to this string.
	 * 
	 * @param Assembly
	 *            the assembly to work on
	 */
	public void workOn(Assembly a) {
		Token t = (Token) a.pop();
		Coffee c = (Coffee) a.getTarget();
		c.setRoast(t.sval().trim());
	}
}