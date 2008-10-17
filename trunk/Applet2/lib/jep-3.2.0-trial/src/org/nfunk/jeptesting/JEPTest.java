
package org.nfunk.jeptesting;

import org.nfunk.jep.JEP;

import com.singularsys.jep.VariableTable;
import com.singularsys.jep.standard.Complex;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JEPTest extends TestCase {

	/** The parser */
	JEP jep;
		
	public JEPTest(String name) {
		super(name);
	}
	
	/**
	 * Sets up the parser.
	 */
	public void setUp() {
		// Set up the parser
		jep = new JEP();
		jep.setImplicitMul(true);
		jep.addStandardFunctions();
		jep.addStandardConstants();
		jep.addComplex();
		jep.setTraverse(false);
	}
	
	/**
	 * Test the getValue() method.
	 */
	public void testGetValue() {
		// Test whether a normal double value is returned correctly
		jep.parseExpression("2.1345");
		double result = jep.getValue();
		Assert.assertTrue(result == 2.1345);
		
		// Test whether NaN is returned for Complex numbers
		jep.parseExpression("i");
		Assert.assertTrue(Double.isNaN(jep.getValue()));
		
		// Test whether NaN is returned for String results
		jep.parseExpression("\"asdf\"");
		Assert.assertTrue(Double.isNaN(jep.getValue()));
	}
	
	/**
	 * Test changing variables value after parsing
	 */
	public void testChangeVariable() {
		Complex result;
		Complex xvar;
		// add the complex variable x = 0,0
		xvar = jep.addVariable("x", 0, 0);
		// parse a simple expression with the variable x
		jep.parseExpression("x");
		jep.getVarValue("x");
		result = jep.getComplexValue();
		Assert.assertTrue(result.re() == 0);
		Assert.assertTrue(result.im() == 0);
		// change the value of x
		jep.addVariable("x", 1, 1);
		jep.getVarValue("x");
		result = jep.getComplexValue();
		Assert.assertTrue(result.re() == 1);
		Assert.assertTrue(result.im() == 1);
	}
	
	/**
	 * Test the getComplexValue() method.
	 */
	public void testGetComplexValue() {
		// Test whether a normal double value is returned as a Complex
		jep.parseExpression("2.1345");
		Assert.assertTrue(new Complex(2.1345, 0).equals(
							jep.getComplexValue(), 0));
		
		// Test whether (0, 1) is returned for i
		jep.parseExpression("i");
		Complex z = jep.getComplexValue();
		Assert.assertTrue(z != null);
		Assert.assertTrue(z.re() == 0);
		Assert.assertTrue(z.im() == 1);
		
		// Test whether NaN is returned for String results
		jep.parseExpression("\"asdf\"");
		Assert.assertTrue(Double.isNaN(jep.getValue()));
	}

	/**
	 * Tests whether allowUndeclared is working properly. 
	 *
	 */
	public void testSetAllowUndeclared() {
		// test whether setAllowUndeclared(true) works
		jep.initSymTab();				// clear the Symbol Table
		jep.setAllowUndeclared(true);
		jep.parseExpression("x");
		VariableTable st = jep.getSymbolTable();
		
		// should only contain a single variable x
		Assert.assertTrue(st.size()==1);
		Assert.assertTrue(st.getVariable("x") != null);
		
		// test whether setAllowUndeclared(false) works
		jep.initSymTab();
		jep.addVariable("x", new Double(1));
		jep.setAllowUndeclared(false);
		jep.parseExpression("p");
		// since p is not declared, an error should occur
		Assert.assertTrue(jep.hasError());
	}
}
