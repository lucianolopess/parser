/*
 * @(#)Coffee.java	 1.0.0
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

import sjm.utensil.*;

public class Coffee implements PubliclyCloneable {

	protected String name;
	protected String formerName;
	protected String roast;
	protected boolean alsoOfferFrench;
	protected String country;
	protected double price;
	
	/**
	 * Return a copy of this object.
	 * 
	 * @return a copy of this object
	 */
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// this should not happen, since we are Cloneable
			throw new InternalError();
		}
	}
	
	/**
	 * Compares two objects for equality, treating nulls carefullly,
	 * and relying on the first object's implementation of <code>
	 * equals()</code>.
	 *
	 * @param   o1   one object
	 *
	 * @param   o2   the other
	 *
	 * @return  <code>true</code> if the objects are equal and
	 *          <code>false</code> otherwise.
	 */
	public static boolean equal(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return o1 == null && o2 == null;
		}
		return o1.equals(o2);
	}

	/**
	 * Compares this object against the specified object. The 
	 * result is <code>true</code> if and only if the argument is 
	 * not <code>null</code> and is a <code>Coffee</code> object 
	 * whose attributes all equal this object's attributes.
	 *
	 * @param   o   the object to compare with.
	 *
	 * @return  <code>true</code> if the objects are equal and
	 *          <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Coffee)) {
			return false;
		}
		Coffee c = (Coffee) obj;
		if (!equal(name, c.name)) {
			return false;
		}
		if (!equal(formerName, c.formerName)) {
			return false;
		}
		if (!equal(roast, c.roast)) {
			return false;
		}
		if (alsoOfferFrench != c.alsoOfferFrench) {
			return false;
		}
		if (!equal(country, c.country)) {
			return false;
		}
		if (price != c.price) {
			return false;
		}
		return true;
	}
	
	/**
	 * Return true, if we offer a french roast version of this
	 * coffee in addition to another roast.
	 *
	 * @return true, if we offer a french roast version of this
	 * coffee in addition to another roast
	 */
	public boolean getAlsoOfferFrench() {
		return alsoOfferFrench;
	}
	
	/**
	 * Return the country of origin for of this type of coffee's
	 * beans.
	 *
	 * @return the country of origin for of this type of coffee's
	 *         beans
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Return a former name for this type of coffee.
	 *
	 * @return a former name for this type of coffee
	 */
	public String getFormerName() {
		return name;
	}
	
	/**
	 * Return the name of this type of coffee.
	 *
	 * @return the name of this type of coffee
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the price per pound of this coffee.
	 *
	 * @return the price per pound of this coffee
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Return the name of the roast of this coffee.
	 *
	 * @return the name of the roast of this coffee
	 */
	public String getRoast() {
		return roast;
	}
	
	/**
	 * Set the truth of the notion that, in addition to some
	 * other type of roast, we offer a french roast version of 
	 * this coffee.
	 *
	 * @param   boolean   true, if we offer a french roast 
	 *                    version of this coffee
	 */
	public void setAlsoOfferFrench(boolean alsoOfferFrench) {
		this.alsoOfferFrench = alsoOfferFrench;
	}
	
	/**
	 * Set the country of origin for of this type of coffee's
	 * beans.
	 *
	 * @param   String   the country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Set a former name of this coffee.
	 *
	 * @param   String   the name
	 */
	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}
	
	/**
	 * Set the name of this coffee.
	 *
	 * @param   String   the name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set the price per pound of this coffee
	 *
	 * @param   double   the price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Set the roast of this coffee. 
	 *
	 * @param   String   the roast
	 */
	public void setRoast(String roast) {
		this.roast = roast;
	}
	
	/**
	 * Return a textual description of this coffee type.
	 * 
	 * @return a textual description of this coffee type
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(name);
		if (formerName != null) {
			buf.append('(');
			buf.append(formerName);
			buf.append(')');
		}
		
		buf.append(", ");
		buf.append(roast);
		if (alsoOfferFrench) {
			buf.append("/French");
		}
		
		buf.append(", ");
		buf.append(country);
		
		buf.append(", ");
		buf.append(price);
		return buf.toString();
	}
}
