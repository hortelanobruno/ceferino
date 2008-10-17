package com.singularsys.jeptests;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.OperatorTable;
import com.singularsys.jep.ParseException;
import com.singularsys.jep.VariableTable;
import com.singularsys.jep.configurableparser.ConfigurableParser;
import com.singularsys.jep.configurableparser.StandardConfigurableParser;
import com.singularsys.jep.functions.Logical;
import com.singularsys.jep.parser.Node;
import com.singularsys.jep.standard.Complex;
import com.singularsys.jep.Operator;

/**
 * This class is intended to contain all tests related to reported bugs.
 * 
 * @author Nathan Funk
 */
public class BugsTest extends TestCase {
	private Jep jep;

	/**
	 * Creates a new BugsTest instance
	 */
	public BugsTest(String name) {
		super(name);
	}
	
	public void setUp() {
		// Set up the parser
		jep = new Jep();
		jep.setImplicitMul(true);
	}
	
	/**
	 * Tests a bug that lead the FractalCanvas example to fail.
	 * (09/04/2007)
	 */
	public void testFractalBug() {
		System.out.println("Testing FractalCanvas bug...");
		Jep jep;
		Complex c;
		
		//Init Parser
		jep = new Jep();

		//Add and initialize x to (0,0)
		jep.addVariable("x", new Complex(0, 0));

		//Parse the new expression
		try {
			jep.parse("x");
		} catch (ParseException e) {
			Assert.fail("Error while parsing: "+e.getMessage());
		}
		//Reset the values
		jep.addVariable("x", new Complex(1, 1));
		//z.set(0,0);
		//System.out.println("x= " + jep.getVarValue("x"));
		
		Object value;
		
		try {
			value = jep.evaluate();
		} catch (EvaluationException e) {
			Assert.fail("Error during evaluation: "+e.getMessage());
			return;
		}
		
		System.out.println("result = " + value);
		Assert.assertTrue(value instanceof Complex);
		c = (Complex)value;
		Assert.assertTrue(c.re() == 1);
		Assert.assertTrue(c.im() == 1);
	}
	
	
	/**
	 * Tests bug [ 1585128 ] setAllowUndeclared does not work!!!
	 * setAllowedUndeclared should add variables to the symbol table.
	 * 
	 * This test parses the expression "x" and checks whether only the
	 * variable x is in the symbol table (no more no less)
	 */
	public void testSetAllowUndeclared() {
		System.out.println("Testing setAllowUndeclared...");
		jep.getVariableTable().clear();				// clear the variable table
		jep.setAllowUndeclared(true);
		try {
			jep.parse("x");
		} catch (ParseException e) {
			Assert.fail();
		}
		VariableTable vt = jep.getVariableTable();
		
		// should only contain a single variable x
		Assert.assertTrue(vt.size()==1);
		Assert.assertTrue(vt.getVariable("x") != null);
	}
	
	/**
	 * Tests [ 1589277 ] Power function and "third root".
	 * 
	 * Simple test for (-8)^(1/3) == -2.
	 *
	public void testComplexPower() {
		jep.initSymTab();
		jep.parseExpression("(-8)^(1/3)");
		Complex result = jep.getComplexValue();
		Assert.assertTrue(result.equals(new Complex(-2, 0)));
	}*/
	
	/**
	 * Tests [ 1563324 ] getValueAsObject always return null after an error
	 * 
	 * JEP 2.4.0 checks the <code>errorList</code> variable before evaluating 
	 * an expression if there is an error in the list, null is returned. This
	 * behaviour is bad because errors are added to the list by
	 * getValueAsObject. If the first evaluation fails (after a successful parse)
	 * then an error is added to the list. Subsequent calls to getValueAsObject
	 * fail because there is an error in the list.
	 */
//	public void testBug1563324() {
//		jep.initSymTab();
//		jep.setAllowUndeclared(true);
//		// parse a valid expression
//		jep.parseExpression("abs(x)");
//		// add a variable with a value that causes evaluation to fail
//		// (the Random type is not supported by the abs function)
//		jep.addVariable("x", new java.util.Random()); 
//		Object result = jep.getValueAsObject();
//		// evaluation should have failed
//		Assert.assertTrue(jep.hasError());
//		
//		// change the variable value to a value that should be evaluated
//		jep.addVariable("x", -1);
//		// ensure that it is evaluated correctly
//		result = jep.getValueAsObject();
//		Assert.assertTrue((result instanceof Double) && ((Double)result).doubleValue() == 1.0);
//	}

	/**
	 * Tests bug 49. Adding an operator such as "AND" does not work. Instead
	 * of being interpreted as and operator it is parsed as a variable.
	 */
	public void testBug49() {
		System.out.println("Testing bug 49...");
		Jep j = new Jep();
		//set configurable parser
		ConfigurableParser cp = new StandardConfigurableParser();
		j.setComponent(cp);

		// alter operator table
		OperatorTable ot = j.getOperatorTable();
		Operator andOp = new Operator("AND", new Logical(0), Operator.BINARY+Operator.LEFT+Operator.ASSOCIATIVE);
		ot.replaceOperator(ot.getAnd(),andOp);
		j.reinitializeComponents();

		try {
			// parse a simple expression
			j.parse("1 AND 1");
			Node n = j.getLastRootNode();
			System.out.println(n.getClass().toString());
			
			// should be a single operator node with two children
			JepTest.nodeTest(n, andOp);
			Assert.assertEquals(2, n.jjtGetNumChildren());
			// children should be constants with no children
			JepTest.nodeTest(n.jjtGetChild(0), new Double(1));
			JepTest.nodeTest(n.jjtGetChild(1), new Double(1));
			
			// try evaluating the expression
			Object result = j.evaluate();
			Assert.assertTrue(result instanceof Boolean);
		} catch (Exception e) {
			// some other exception was thrown
			System.out.println(e.getMessage());
			//e.printStackTrace();
			Assert.fail();
		}
	}
}
