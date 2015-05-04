/*
 * @(#)WordState.java	 1.0.0
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

package sjm.parse.tokens;

import java.io.IOException;
import java.io.PushbackReader;

public class WordState extends TokenizerState {

	@Override
	public Token nextToken(PushbackReader r, int c, Tokenizer t)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWordChars(char c, char d, boolean b) {
		// TODO Auto-generated method stub
		
	}

}