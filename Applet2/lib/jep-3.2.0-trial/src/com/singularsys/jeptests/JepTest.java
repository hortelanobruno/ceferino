
package com.singularsys.jeptests;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;

import com.singularsys.jep.*;
import com.singularsys.jep.configurableparser.ConfigurableParser;
import com.singularsys.jep.configurableparser.StandardConfigurableParser;
import com.singularsys.jep.functions.LazyLogical;
import com.singularsys.jep.functions.PostfixMathCommand;
import com.singularsys.jep.functions.StrictNaturalLogarithm;
import com.singularsys.jep.misc.MacroFunction;
import com.singularsys.jep.parser.ASTConstant;
import com.singularsys.jep.parser.ASTFunNode;
import com.singularsys.jep.parser.ASTOpNode;
import com.singularsys.jep.parser.ASTStart;
import com.singularsys.jep.parser.ASTVarNode;
import com.singularsys.jep.parser.Node;
import com.singularsys.jep.standard.Complex;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JepTest extends TestCase {
	public static final boolean PRINT_RESULTS = true;
	/** The parser */
	protected Jep jep;
		
	public JepTest(String name) {
		super(name);
	}
	
	/**
	 * Sets up the parser.
	 */
	public void setUp() {
		// Set up the parser
		jep = new Jep();
		jep.setImplicitMul(true);
		//jep.addStandardFunctions();
		jep.addStandardConstants();
		//jep.addComplex();
		//jep.setTraverse(false);
	}
	
	/*------------------- utility functions -------------------------------*/
	
	/**
	 * Prints a header with the name of the test as specified in str.
	 */
	protected void printTestHeader(String str) {
		System.out.println("\n\n------------------------------------------------------------------------");
		System.out.println("Running \""+str+"\"\n");
	}
	
	/**
	 * Test result j.evaluate(j.parse(expr))
	 * @param expr the expression to parse and evaluate
	 * @param expected result expected
	 * @throws Exception
	 */
	protected void valueTest(String expr,Object expected) throws Exception
	{
		Node node = jep.parse(expr);
		Object res = jep.evaluate(node);
		
		myAssertEquals(expr,expected,res);
	}

	/**
	 * Calculate the value of an expression.
	 * @param node
	 * @throws Exception
	 */
	protected Object calcValue(Node node) throws Exception {
		Object res = jep.evaluate(node);
		return res;
	}

	/**
	 * Calculate the value of an expression.
	 * @param expr
	 * @throws Exception
	 */
	protected Object calcValue(String expr) throws Exception {
		Node node = jep.parse(expr);
		Object res = calcValue(node);
		return res;
	}

	protected void myAssertEquals(String msg, Object expected, Object actual) {
		if(PRINT_RESULTS && !actual.equals(expected))
			System.out.println("Error: '"+msg+"' is '"+actual+"' should be '"+expected+"'");
		assertEquals("<"+msg+">",expected,actual);
		if(PRINT_RESULTS)
			System.out.println("Success: Value of \""+msg+"\" is "+actual+"");
	}

	public void myAssertNaN(String msg,Object actual)
	{
		if(actual instanceof Double) {
			if(Double.isNaN( ((Double) actual).doubleValue()) ) {
				System.out.println("Success: Value of \""+msg+"\" is "+actual+"");
			}
			else {
				System.out.println("Error: \""+msg+"\" is '"+actual+"' should be NaN");
				fail("<"+msg+"> is "+actual+" should be NaN");
			}
		}
		else {
			System.out.println("Error: '"+msg+"' is '"+actual+"' should be 'NaN'");
			fail("<"+msg+">");
		}
	}

	/**
	 * Test whether an expression evaluates to NaN
	 * @param expr
	 * @throws Exception
	 */
	public void valueTestNaN(String expr) throws Exception
	{
		Object res = calcValue(expr);
		myAssertNaN(expr,res);
	}

	public void valueTestString(String expr,String expected) throws Exception
	{
		Object res = calcValue(expr);
		myAssertEquals(expr,expected,res.toString());
	}
	/** Test parse-evaluate with complex number and given tolerence.
	 * 
	 * @param expr
	 * @param expected
	 * @param tol
	 * @throws Exception
	 */
	public void complexValueTest(String expr,Complex expected,double tol) throws Exception
	{
		Object res = calcValue(expr);
		if(expected.equals((Complex) res,tol))
			System.out.println("Success value of \""+expr+"\" is "+res);
		else {
			System.out.println("Error value of \""+expr+"\" is "+res+" should be "+expected);
			fail("<"+expr+"> expected: <"+expected+"> but was <"+res+">");
		}
	}

	/**
	 * Test values to within a given precision
	 * 
	 * @param expr
	 *            expression
	 * @param a
	 *            the expected value
	 * @param tol
	 *            tolerance
	 * @throws Exception
	 */
	public void valueTest(String expr, double a, double tol) throws Exception {
		Object res = calcValue(expr);
		if (res instanceof Double) {
			double val = ((Double) res).doubleValue();
			if (Math.abs(val - a) < tol) {
				System.out.println("Success value of \"" + expr + "\" is "
						+ res);
			} else {
				System.out.println("Error value of \"" + expr + "\" is " + res
						+ " should be " + a);
				assertEquals(expr, a, val, tol);
			}
		} else {
			System.out.println("Error value of \"" + expr + "\" is " + res
					+ " should be " + a);
			fail("<" + expr + "> expected: <" + a + "> but was <" + res + ">");
		}
	}

	static public void nodeTest(Node n, Operator op) {
		assertTrue("Node " + n.toString() + "should have been an ASTOpNode",
				n instanceof ASTOpNode);
		assertEquals(op, ((ASTOpNode) n).getOperator());
	}

	static public void nodeTest(Node n, String name) {
		assertTrue("Node " + n.toString() + "should have been an ASTFunNode",
				n instanceof ASTFunNode);
		assertEquals(name, ((ASTFunNode) n).getName());
	}

	static public void nodeTest(Node n, Variable v) {
		assertTrue("Node " + n.toString() + "should have been an ASTVarNode",
				n instanceof ASTVarNode);
		assertEquals(v, ((ASTVarNode) n).getVar());
	}

	static public void nodeTest(Node n, Object v) {
		assertTrue("Node " + n.toString() + "should have been an ASTConstant node",
				n instanceof ASTConstant);
		assertEquals(v, ((ASTConstant) n).getValue());
	}
	
	/*------------------------- tests ---------------------------------------*/

	public void testSimpleSum() throws Exception
	{
	    printTestHeader("Testing very simple sums...");

		valueTest("1+2",3.0);		
		valueTest("2*6+3",15.0);		
		valueTest("2*(6+3)",18.0);
	}

	/**
	 * Tests the evaluate() method.
	 */
	public void testEvaluate() throws Exception {
	    printTestHeader("Testing evaluation results...");
			// test a very basic expression
			Object result = jep.evaluate(jep.parse("2.1345"));
			Assert.assertTrue(result instanceof Double);
			Assert.assertTrue(result.equals(2.1345));

			// Test Complex numbers
			result = jep.evaluate(jep.parse("i"));
			Assert.assertTrue(result instanceof Complex);
			Assert.assertTrue(result.equals(new Complex(0,1)));
			
			// Test whether a String is passed through
			result = jep.evaluate(jep.parse("\"asdf\""));
			Assert.assertTrue(result instanceof String);
			Assert.assertEquals("asdf",result);
	}
	
	/**
	 * Test changing variables value after parsing
	 */
	public void testChangeVariable() {
		Object result;
		Complex c;
		// add the complex variable x = 0,0
		jep.addVariable("x", new Complex(0, 0));
		// parse a simple expression with the variable x
		
		try {
			jep.parse("x");
		} catch (ParseException e) {
			Assert.fail("Exception occured");
		}
//		jep.getVarValue("x");
		try {
			result = jep.evaluate();
		} catch (EvaluationException e) {
			Assert.fail("Exception occured"+e.getMessage());
			return;
		}
		Assert.assertTrue(result instanceof Complex);
		c = (Complex)result;
		Assert.assertTrue(c.re() == 0);
		Assert.assertTrue(c.im() == 0);
		// change the value of x
		jep.addVariable("x", new Complex(1, 1));
//		jep.getVarValue("x");
		try {
			result = jep.evaluate();
		} catch (EvaluationException e) {
			Assert.fail("Exception occured");
			return;
		}
		Assert.assertTrue(result instanceof Complex);
		c = (Complex)result;
		Assert.assertTrue(c.re() == 1);
		Assert.assertTrue(c.im() == 1);
	}
	

	/**
	 * Tests whether allowUndeclared is working properly. 
	 *
	 */
	public void testSetAllowUndeclared() throws Exception {
	    printTestHeader("Testing AllowedUndeclared options...");

		// test whether setAllowUndeclared(true) works
		jep.getVariableTable().clear();				// clear the Variable Table
		jep.setAllowUndeclared(true);
		try {
			jep.parse("x");
		} catch (ParseException e) {
			Assert.fail("Exception occurred "+e.getMessage());
		}
		VariableTable st = jep.getVariableTable();
		
		// should only contain a single variable x
		Assert.assertTrue(st.size()==1);
		Assert.assertTrue(st.getVariable("x") != null);
		
		try {
		    jep.evaluate();
		    Assert.fail("Exception should have been thrown when trying to evaluate a declared but undefined variable");
		} catch(EvaluationException e) {
			// exception was thrown, so all is well
		}
		
		jep.setDefaultValue(Double.valueOf(0.0));
		jep.parse("y");
		Object val = jep.evaluate();
		assertEquals("Value of y using default value",0.0,val);
		
		// test whether setAllowUndeclared(false) works
		jep.getVariableTable().clear();
		jep.addVariable("x", new Double(1));
		jep.setAllowUndeclared(false);
		try {
			jep.parse("p");
			// since p is not declared, an error should occur
			Assert.fail("A ParseException should have been thrown creating variable 'p'.");
		} catch (ParseException e) {
			// exception was thrown, so all is well
		}
	}


	public void testNumbers() throws Exception
	{
	    printTestHeader("Testing Numbers...");
	
		valueTest("0",0.0);
		valueTest("0.",0.0);
		valueTest(".0",0.0);
		valueTest("0.0",0.0);
		valueTest("-0",-0.0);
		valueTest("-0.0",-0.0);
		valueTest("1",1.0);
		valueTest("1.",1.0);
		valueTest("1.0",1.0);
		valueTest("-1",-1.0);
		valueTest("-1.",-1.0);
		valueTest("-1.0",-1.0);
		valueTest("1.5",1.5);
		valueTest(".5",0.5);
		valueTest("0.5",0.5);
		valueTest("-1.5",-1.5);
		valueTest("-.5",-0.5);
		valueTest("-0.5",-0.5);

		valueTest("5e2",500.0);
		valueTest("5E2",500.0);
		valueTest("5e-2",0.05);
		valueTest("5e-2",0.05);
		valueTest("-5e2",-500.0);
		valueTest("-5E2",-500.0);
		valueTest("-5e-2",-0.05);
		valueTest("-5e-2",-0.05);

		valueTest(".5e3",500.0);
		valueTest(".5E3",500.0);
		valueTest(".5e-1",0.05);
		valueTest(".5e-1",0.05);
		valueTest("-.5e3",-500.0);
		valueTest("-.5E3",-500.0);
		valueTest("-.5e-1",-0.05);
		valueTest("-.5e-1",-0.05);

		valueTest("0.5e3",500.0);
		valueTest("0.5E3",500.0);
		valueTest("0.5e-1",0.05);
		valueTest(".5e-1",0.05);
		valueTest("-.5e3",-500.0);
		valueTest("-.5E3",-500.0);
		valueTest("-.5e-1",-0.05);
		valueTest("-.5e-1",-0.05);

		valueTest("1.5e3",1500.0);
		valueTest("1.5E3",1500.0);
		valueTest("1.5e-1",0.15);
		valueTest("1.5e-1",0.15);
		valueTest("-1.5e3",-1500.0);
		valueTest("-1.5E3",-1500.0);
		valueTest("-1.5e-1",-0.15);
		valueTest("-1.5e-1",-0.15);

	}
	
	public void testStrings() throws Exception
	{
	    printTestHeader("Testing strings...");

	    valueTest("\"\"","");
	    valueTest("\"a\"","a");
	    valueTest("\"abcdefghijklmnopqrstuvwxyz\"","abcdefghijklmnopqrstuvwxyz");
	    valueTest("sum(\"a\",\"b\")","ab");
	    valueTest("\"a\\\"b\"","a\"b");
	    valueTest("\"A\\bB\\fC\\nD\\rE\\tF\"","A\bB\fC\nD\rE\tF");
	    valueTest("A=\"1\"","1");
	    valueTest("B=\"2\"","2");
	    valueTest("(A==\"1\")&&(B==\"2\")",myTrue);
	}

	public void testLogical() throws Exception
	{
	    printTestHeader("Testing logical operations");

		valueTest("T=1",1.0);
		valueTest("F=0",0.0);
		valueTest("!T",myFalse);
		valueTest("!F",myTrue);
		valueTest("!5",myFalse);
		valueTest("-0==0",myTrue);
		valueTest("!-5",myFalse);
		//valueTest("-!5==0",myTrue);
		//valueTest("-!0",-1.0);
		valueTest("T&&T",myTrue);
		valueTest("T&&F",myFalse);
		valueTest("F&&T",myFalse);
		valueTest("F&&F",myFalse);
		valueTest("T||T",myTrue);
		valueTest("T||F",myTrue);
		valueTest("F||T",myTrue);
		valueTest("F||F",myFalse);
		calcValue("a=F"); calcValue("b=F"); calcValue("c=F");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);
		calcValue("a=F"); calcValue("b=F"); calcValue("c=T");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);
		calcValue("a=F"); calcValue("b=T"); calcValue("c=F");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);
		calcValue("a=F"); calcValue("b=T"); calcValue("c=T");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);

		calcValue("a=T"); calcValue("b=F"); calcValue("c=F");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);
		calcValue("a=T"); calcValue("b=F"); calcValue("c=T");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);
		calcValue("a=T"); calcValue("b=T"); calcValue("c=F");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);
		calcValue("a=T"); calcValue("b=T"); calcValue("c=T");
		valueTest("(a&&(b||c)) == ((a&&b)||(a&&c))",myTrue);
		valueTest("(a||(b&&c)) == ((a||b)&&(a||c))",myTrue);
		
		jep.addVariable("true",myTrue);
		jep.addVariable("false",myFalse);
		valueTest("true",myTrue);
		valueTest("false",myFalse);
		valueTest("!true",myFalse);
		valueTest("!false",myTrue);
		valueTest("true==true",myTrue);
		valueTest("false==false",myTrue);
		valueTest("true==false",myFalse);
		valueTest("true==true&&false==false",myTrue);
		valueTest("if(true==true&&false==false,6,7)",6.0);
		valueTest("if(false&&true,6,7)",7.0);
		valueTest("if(true&&false==false,6,7)",6.0);
		valueTest("if((true&&true)==true,6,7)",6.0);
		valueTest("if((!false)==true,6,7)",6.0);
	}

	protected Object myTrue = Boolean.TRUE; 
	protected Object myFalse = Boolean.FALSE; 
	public void testNaN() throws Exception
	{
		printTestHeader("Testing for NaN");
		jep.addVariable("x",new Double(Double.NaN));
		System.out.println("x=NaN");
		valueTestNaN("ln(x)");
		valueTestNaN("log(x)");
		valueTestNaN("sin(x)");
		valueTestNaN("x+x");
		valueTest("x!=x",myTrue);
		valueTest("x==x",myFalse);

		jep.addVariable("y",new Double(Double.NaN));
		Node n = jep.parse("x+5");
		System.out.println(calcValue(n));
		Node n2 = jep.parse("y");
		System.out.println(calcValue(n2));
		valueTest("x == x+5",myFalse);
		valueTest("x == 0/0",myFalse);
		valueTest("x == x",myFalse);
		valueTest("x == 0 * x",myFalse);
		valueTest("x == 5",myFalse);
		valueTest("x == y",myFalse);
		valueTest("y == y",myFalse);
		System.out.println("Set x to Double(5)");
		jep.addVariable("x",new Double(5));
		valueTest("x == x+5",myFalse);
		valueTest("x == x",myTrue);
	}

	public void testComplex() throws Exception
	{
		printTestHeader("Testing complex values");
		double tol = 0.00000001;

		complexValueTest("z=complex(3,2)",new Complex(3,2),tol);
		complexValueTest("z*z-z",new Complex(2,10),tol);
		complexValueTest("z^3",new Complex(-9,46),tol);
		complexValueTest("(z*z-z)/z",new Complex(2,2),tol);
		complexValueTest("w=polar(2,pi/2)",new Complex(0,2),tol);
		
		complexValueTest("ln(-1)",new Complex(0,Math.PI),tol);
		complexValueTest("sqrt(-1)",new Complex(0,1),tol);
		complexValueTest("pow(-1,0.5)",new Complex(0,1),tol);
		valueTest("arg(w)",Math.PI/2);
		valueTest("cmod(w)",2.0);
		valueTest("re(z)",3.0);
		valueTest("im(z)",2.0);
		complexValueTest("conj(z)",new Complex(3,-2),tol);
		complexValueTest("exp(pi*i/2)",new Complex(0,1),tol);
		//complexValueTest("cos(z)",new Complex(3,-2),tol);
	}

	public void testFunction() throws Exception
	{
		printTestHeader("Testing real functions");
		valueTest("abs(2.5)",2.5);
		valueTest("abs(-2.5)",2.5);
		valueTest("acos(1/sqrt(2))",Math.PI/4,0.00000001);
		valueTest("cos(pi/3)",0.5,0.00000001);
		valueTest("atan2(3,4)",Math.atan2(3.0,4.0),0.00000001);
		valueTest("2^4",16.0);
		valueTest("2^10",1024.0);
		valueTest("2^0.5",Math.sqrt(2),0.00000001);
		valueTest("2^(-0.5)",1.0/Math.sqrt(2),0.00000001);
		valueTest("2^(-4)",1.0/16,0.00000001);
		valueTest("2^(-10)",1.0/1024,0.00000001);
		calcValue("rand()");
		valueTest("4 atan2(1,1)",Math.PI,0.00000001);
	}

	public void testIf()  throws Exception
	{
		printTestHeader("Testing if statement");
		valueTest("if(1,2,3)",2.0);		
		valueTest("if(-1,2,3)",3.0);		
		valueTest("if(0,2,3)",3.0);		
		valueTest("if(1,2,3,4)",2.0);		
		valueTest("if(-1,2,3,4)",3.0);		
		valueTest("if(0,2,3,4)",4.0);		
		valueTest("if(0>=0,2,3,4)",2.0);		
		valueTest("x=3",3.0);		
		valueTest("if(x==3,1,-1)",1.0);		
		valueTest("if(x!=3,1,-1)",-1.0);		
		valueTest("if(x>=3,1,-1)",1.0);		
		valueTest("if(x>3,1,-1)",-1.0);		
		valueTest("if(x<=3,1,-1)",1.0);		
		valueTest("if(x<3,1,-1)",-1.0);		
	}

	public void testPlusPlus() throws Exception
	{
		printTestHeader("++ notation");
		valueTest("3++2",5.0);
		valueTest("3+-2",1.0);
		valueTest("3-+2",1.0);
		valueTest("3--2",5.0);
		valueTest("3+++2",5.0);
		valueTest("3++-2",1.0);
		valueTest("3+-+2",1.0);
		valueTest("3+--2",5.0);
		valueTest("3-++2",1.0);
		valueTest("3-+-2",5.0);
		valueTest("3--+2",5.0);
		valueTest("3---2",1.0);

	}
	
	public void testImplicitMul() throws Exception
	{
		printTestHeader("Implicit Multiplication");
		valueTest("x=5",5.0);
		valueTest("y=7",7.0);
		valueTest("x2=37",37.0);
		valueTest("2 x",10.0);
		valueTest("2x",10.0);
		valueTest("x 2",10.0);
		valueTest("x2",37.0);
		valueTest("-2 x",-10.0);
		valueTest("x y",35.0);
		valueTest("-x y",-35.0);
		valueTest("x x x x x x x x",Math.pow(5, 8));
		valueTest("3 (2+4)",18.0);
		valueTest("3(2+4)",18.0);
		valueTest("2(x-1)",8.0);
		valueTest("(x+1)(x-1)",24.0);
		valueTest("x(x-1)",20.0);
		valueTest("(x+1)x",30.0);
		valueTest("1+2 3",7.0);
		valueTest("2 3+4",10.0);
		valueTest("1-2 3",-5.0);
		valueTest("2 3 4",24.0);
		valueTest("-(4-2)",-2.0);
	}
	
	public void testUminusPower() throws Exception
	{
	    printTestHeader("Testing precedence of unitary minus and power...");
	    valueTest("-1^2", -1.0);
	    valueTest("2^-3", 0.125);
	}
	
	public void testNumParam() throws Exception
	{
		printTestHeader("Number of parameters");
		jep.parse("if(3,1,2)");
		jep.parse("if(4,1,2,3)");
		try
		{
			jep.parse("if(5,1,2,3,4)");
			fail("Did not trap illegal number of arguments");
		}
		catch(ParseException e) {}
		jep.parse("a1=1234");
		jep.parse("a2=5678");
		jep.parse("ApportionmentAmt=4321");
		jep.parse("a4 = 2000 + (3000 /2000) + (3.45787 * 33544 - (212.223 /2000)) + + 1200");
		jep.parse("a3 = if(a1 > 0 && ApportionmentAmt < 1000, if(a2 < 2000, if(a2 < 1000, 200, 0), if(a1 > 1000, if((2000 + (3000 /2000) + (3.45787 * 33544 - (212.223 /2000)) + 1200 + ApportionmentAmt / 2000 + ApportionmentAmt * ApportionmentAmt + 2000) > 0, 100, 200),200)), if(a1/a2 < 1000, a1/a2, 1, a1 * a2 + a1))");
		try
		{
		 jep.parse("a3 = if(a1 > 0 && ApportionmentAmt < 1000, if(a2 < 2000, if(a2 < 1000, 200, 0), if(a1 > 1000, if((2000 + (3000 /2000) + (3.45787 * 33544 - (212.223 /2000)) + 1200 + ApportionmentAmt / 2000 + ApportionmentAmt * ApportionmentAmt + 2000) > 0, 100, 200)),200), if(a1/a2 < 1000, a1/a2, 1, a1 * a2 + a1))");
			fail("Did not trap illegal number of arguments");
		}
		catch(ParseException e) {}
	}

	public void testBinom() throws ParseException,Exception
	{
		printTestHeader("Testing binomial coeffs");
		valueTest("binom(0,0)",1);
		valueTest("binom(1,0)",1);
		valueTest("binom(1,1)",1);
		valueTest("binom(2,0)",1);
		valueTest("binom(2,1)",2);
		valueTest("binom(2,2)",1);
		valueTest("binom(3,0)",1);
		valueTest("binom(3,1)",3);
		valueTest("binom(3,2)",3);
		valueTest("binom(3,3)",1);
		valueTest("binom(4,0)",1);
		valueTest("binom(4,1)",4);
		valueTest("binom(4,2)",6);
		valueTest("binom(4,3)",4);
		valueTest("binom(4,4)",1);
		valueTest("binom(5,0)",1);
		valueTest("binom(5,1)",5);
		valueTest("binom(5,2)",10);
		valueTest("binom(5,3)",10);
		valueTest("binom(5,4)",5);
		valueTest("binom(5,5)",1);

		valueTest("binom(6,0)",1);
		valueTest("binom(6,1)",6);
		valueTest("binom(6,2)",15);
		valueTest("binom(6,3)",20);
		valueTest("binom(6,4)",15);
		valueTest("binom(6,5)",6);
		valueTest("binom(6,6)",1);
		
		valueTest("binom(10,1)",10);
		valueTest("binom(10,5)",252);
	}

	public void testFormat() throws Exception
	{
		printTestHeader("Format");
		NumberFormat format = NumberFormat.getInstance();
		jep.getPrintVisitor().setNumberFormat(format);
		format.setMaximumFractionDigits(3);
		format.setMinimumFractionDigits(0);
		
		String s1 = "[10,0,0.1,0.11,0.111,0.1111]";
		String r1 = jep.toString(jep.parse(s1));
		this.myAssertEquals(s1,"[10,0,0.1,0.11,0.111,0.111]",r1);
		String s2 = "[0.9,0.99,0.999,0.9999]";
		String r2 = jep.toString(jep.parse(s2));
		this.myAssertEquals(s2,"[0.9,0.99,0.999,1]",r2);
		
		String s3 = "8 - 7.9"; 
		jep.parse(s3);
		double res = jep.evaluateD();
		String s = String.format("%.3f",res);
		this.myAssertEquals(s3,"0.100",s);
		
		String s4 = "round(8-7.9,3)";
		jep.parse(s4);
		res = jep.evaluateD();
		this.myAssertEquals(s4,"0.1",String.valueOf(res));
	}

	public void testAssign()  throws Exception
	{
		printTestHeader("Assignment of variables");
		valueTest("x=3",3.0);
		valueTest("y=3+4",7.0);
		valueTest("z=x+y",10.0);
		valueTest("a=b=c=z",10.0);
		valueTest("b",10.0);
		valueTest("d=f=a-b",0.0);
		valueTest("x=2",2.0);
		valueTest("(x*x)*x*(x*x)",32.0); // Works fine with Multiply
//		new org.lsmp.djep.vectorJep.VectorJep();
		valueTest("(x*x)*x*(x*x)",32.0);
		// this created an error in 2.3.0b
		// as creating a VectorJep changed the operator set
		// and hence the broken MMultiply was used.								
	}

	public void testListAccess() throws Exception
	{
		printTestHeader("List operations");
		valueTestString("x=[4,3,2,1]","[4.0, 3.0, 2.0, 1.0]");
		valueTest("x[2]",3.0);
		valueTest("x[4]=5",5.0);
		valueTestString("x","[4.0, 3.0, 2.0, 5.0]");
		valueTestString("[]","[]");
		valueTestString("x+x","[8.0, 6.0, 4.0, 10.0]");
		valueTestString("x-x","[0.0, 0.0, 0.0, 0.0]");
		valueTestString("x*2","[8.0, 6.0, 4.0, 10.0]");
		valueTestString("2*x","[8.0, 6.0, 4.0, 10.0]");
		valueTestString("x/2","[2.0, 1.5, 1.0, 2.5]");
		valueTestString("w=[1+i,1-i]","[(1.0, 1.0), (1.0, -1.0)]");
		valueTestString("2*w","[(2.0, 2.0), (2.0, -2.0)]");
	}

	public void testMultiDimArray() throws Exception
	{
		printTestHeader("List operations");
		valueTestString("x=[[1,2],[3,4]]","[[1.0, 2.0], [3.0, 4.0]]");
		valueTestString("x[2]","[3.0, 4.0]");
		valueTestString("x+x","[[2.0, 4.0], [6.0, 8.0]]");
		valueTestString("x-x","[[0.0, 0.0], [0.0, 0.0]]");
		valueTestString("x*2","[[2.0, 4.0], [6.0, 8.0]]");
		valueTestString("2*x","[[2.0, 4.0], [6.0, 8.0]]");
		valueTestString("x/2","[[0.5, 1.0], [1.5, 2.0]]");
	}

	public void testLazyLogical() throws Exception
	{
		printTestHeader("Lazy Logical");
	    jep.getOperatorTable().getAnd().setPFMC(new LazyLogical(LazyLogical.AND));
	    jep.getOperatorTable().getOr().setPFMC(new LazyLogical(LazyLogical.OR));
	    class SideEffect extends PostfixMathCommand {
		private static final long serialVersionUID = 1L;
		public SideEffect() { this.numberOfParameters = 1; }
		public boolean called = false;
		public void run(Stack<Object> aStack) throws EvaluationException {
		    called = true;
		}
	    };
	    SideEffect se = new SideEffect();
	    jep.addFunction("sideEffect",se);
	    Node n1 = jep.parse("0 || sideEffect(1==2)");
	    Object v1 = jep.evaluate(n1);
	    Assert.assertTrue(se.called);
	    Assert.assertEquals(myFalse,v1);
	    se.called = false;

	    Node n2= jep.parse("1 || sideEffect(1==2)");
	    Object v2 = jep.evaluate(n2);
	    Assert.assertFalse(se.called);
	    Assert.assertEquals(myTrue,v2);
	    se.called = false;

	    Node n3 = jep.parse("0 && sideEffect(1==2)");
	    Object v3 = jep.evaluate(n3);
	    Assert.assertFalse(se.called);
	    Assert.assertEquals(myFalse,v3);
	    se.called = false;

	    Node n4= jep.parse("1 && sideEffect(1==2)");
	    Object v4 = jep.evaluate(n4);
	    Assert.assertTrue(se.called);
	    Assert.assertEquals(myFalse,v4);
	    se.called = false;

	    Node n5 = jep.parse("0 || sideEffect(2==2)");
	    Object v5 = jep.evaluate(n5);
	    Assert.assertTrue(se.called);
	    Assert.assertEquals(myTrue,v5);
	    se.called = false;

	    Node n6= jep.parse("1 || sideEffect(2==2)");
	    Object v6 = jep.evaluate(n6);
	    Assert.assertFalse(se.called);
	    Assert.assertEquals(myTrue,v6);
	    se.called = false;

	    Node n7 = jep.parse("0 && sideEffect(2==2)");
	    Object v7 = jep.evaluate(n7);
	    Assert.assertFalse(se.called);
	    Assert.assertEquals(myFalse,v7);
	    se.called = false;

	    Node n8= jep.parse("1 && sideEffect(2==2)");
	    Object v8 = jep.evaluate(n8);
	    Assert.assertTrue(se.called);
	    Assert.assertEquals(myTrue,v8);
	    se.called = false;

	    Node n9= jep.parse("if(1,sideEffect(2),3)");
	    Object v9 = jep.evaluate(n9);
	    Assert.assertTrue(se.called);
	    Assert.assertEquals(new Double(2),v9);
	    se.called = false;

	    Node n10= jep.parse("if(1,2,sideEffect(3))");
	    Object v10 = jep.evaluate(n10);
	    Assert.assertFalse(se.called);
	    Assert.assertEquals(new Double(2),v10);
	    se.called = false;

	    Node n11= jep.parse("if(0,sideEffect(2),3)");
	    Object v11 = jep.evaluate(n11);
	    Assert.assertFalse(se.called);
	    Assert.assertEquals(new Double(3),v11);
	    se.called = false;

	    Node n12= jep.parse("if(0,2,sideEffect(3))");
	    Object v12 = jep.evaluate(n12);
	    Assert.assertTrue(se.called);
	    Assert.assertEquals(new Double(3),v12);
	    se.called = false;
	}
	
	public void testLogarithm() throws Exception
	{
		printTestHeader("Logarithm");
	    FunctionTable ft = jep.getFunctionTable();
	    ft.addFunction("slog",new StrictNaturalLogarithm());
	    ft.addFunction("slog10",new StrictNaturalLogarithm(10));
	    ft.addFunction("slog2",new StrictNaturalLogarithm(2));
	    jep.addVariable("NaN",Double.NaN);
	    valueTest("ln(1)",0.0,1E-7);
	    valueTest("slog(1)",0.0,1E-7);
	    valueTest("slog10(1)",0.0,1E-7);
	    valueTest("ln(e)",1.0,1E-7);
	    valueTest("slog(e)",1.0,1E-7);
	    valueTest("slog10(10)",1.0,1E-7);
	    valueTestNaN("slog(-1)");
	    valueTestNaN("slog10(-1)");
	    valueTestNaN("slog2(-1)");
	}
	
	/**
	 * Test creating a Jep instance with no functions and no variables.
	 * @throws Exception
	 */
	public void testBlankParser() throws Exception
	{
		printTestHeader("Testing blank parser");
		Jep j = new Jep();
		// override standard variable table with blank one
		j.setComponent(new VariableTable());
		// override standard function table with blank one
		j.setComponent(new FunctionTable());
		
		// ensure function and variable tables are blank
		Assert.assertTrue(j.getFunctionTable().size() == 0);
		Assert.assertTrue(j.getVariableTable().size() == 0);
		// ensure operators still work
		j.parse("1+1");
		Object res = j.evaluate();
		Assert.assertTrue(res instanceof Double);
		Assert.assertTrue(((Double)res).doubleValue()==2.0);
	}
	
	public void testMacroFunction() throws Exception
	{
	    printTestHeader("Testing MacroFunctions...");
	    MacroFunction fact = new MacroFunction(
		    "fact",new String[]{"x"}, 
		    "if(x>1,x*fact(x-1),1)");
	    jep.addFunction("fact",fact);
	    fact.init(jep);
	    valueTest("fact(5)",120.0);
	    jep.addVariable("x",4);
	    valueTest("fact(5)",120.0);
	    Assert.assertEquals(4.0,jep.getVariableValue("x"));
	    valueTest("x+fact(x+1)",124.0);
	    
	}
	
	/**
	 * Tests whether a parse exceptions are thrown when they should be. Tests
	 * on both the standard parser and the configurable parser. See ticket #50
	 */
	public void testParseException() {
		printTestHeader("Testing ParseException...");
		// set up a new default parser and run test
		Jep j = new Jep();
		parseExceptionWithSpecParser(j);
		// set up a configurable parser
		j = new Jep();
		ConfigurableParser cp = new StandardConfigurableParser();
		j.setComponent(cp);
		parseExceptionWithSpecParser(j);
	}

	/**
	 * Called by testParseException with a specific parser instance
	 * @param j the parser instance
	 */
	public void parseExceptionWithSpecParser(Jep j) {
		// all these expressions should cause parseexceptions
		String expr[] = {"1+", "(", ")", "sin(", "(1+)", "*1", "1*", "-"};
		
		// for each expression
		for (int i=0;i<expr.length;i++) {
			try {
				System.out.println("Attempting parse of \"" + expr[i]+ "\"");
				j.parse(expr[i]);
				// expression should have failed to parse
				Assert.fail();
			} catch (ParseException e) {
				// expected a parse exception has been thrown
				System.out.println("Caught expected exception \"" + e.getMessage()+"\"");
			} catch (Exception e) {
				// some other exception was thrown
				Assert.fail();
			}
		}
	}

	/**
	 * Test whether evaluation exceptions are thrown when they should be.
	 */
	public void testEvalExceptions() {
		printTestHeader("Ensuring evaluation exception are thrown when they should be...");
		ArrayList<String> exprs = new ArrayList<String>();

		// random expressions that will fail during evaluation
		exprs.add("avg()");
		exprs.add("min()");
		exprs.add("max()");
		exprs.add("avg([])");
		exprs.add("min([])");
		exprs.add("max([])");

		// add expressions like sin([1, 1]) that can not handle arrays
		String noArrayFuncs[] = { // list of functions that can not take arrays as arguments
				"sin", "cos", "tan", "asin", "acos", "atan", 
				"sinh", "cosh", "tanh", "asinh", "acosh", "atanh",
				"log", "ln", "exp"
		};
		for (String funcname:noArrayFuncs) {
			exprs.add(funcname+"([])");
			exprs.add(funcname+"([1,1])");			
		}
		
		// loop through each exception
		for (String str:exprs) {
			// parsing should work fine
			try {
				jep.parse(str);
			} catch (ParseException e) {
				Assert.fail("Could not parse expression: \"" + str + "\", "+e.getMessage());
			}
			// but evaluation should fail
			try {
				jep.evaluate();
				// we shouldn't get here because these expressions should
				// end up throwing eval expections
				Assert.fail("No evaluation exception was thrown as expected.");
			} catch (EvaluationException e) {
				// we should end up here
			}
		}
	}
	
	public void testSemiColon() throws Exception
	{
	 String s = "x=5; y=6; x+y";
	printTestHeader("Testing reading multiple equations: "+s);
	 jep.initMultiParse(s);
	 Node n1 = jep.continueParsing();
	 Node n2 = jep.continueParsing();
	 Node n3 = jep.continueParsing();
	 Object o1 = jep.evaluate(n1);
	 this.myAssertEquals("x=5", 5.0,o1);
	 Object o2 = jep.evaluate(n2);
	 this.myAssertEquals("y=6", 6.0,o2);
	 Object o3 = jep.evaluate(n3);
	 this.myAssertEquals("x+y", 11.0,o3);
	 Node n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);
	}

	public void testMultiLine() throws Exception
	{
	 String s = "\nx=\n5;y=6;\nx+y";
	printTestHeader("Testing reading multiple equations: "+s);
	 jep.initMultiParse(s);
	 
	 Node n1 = jep.continueParsing();
	 Object o1 = jep.evaluate(n1);
	 this.myAssertEquals("x=5", 5.0,o1);

	 Node n2 = jep.continueParsing();
	 Object o2 = jep.evaluate(n2);
	 this.myAssertEquals("y=6", 6.0,o2);

	 Node n3 = jep.continueParsing();
	 Object o3 = jep.evaluate(n3);
	 this.myAssertEquals("x+y", 11.0,o3);

	 Node n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);
	 
	 /******/
	 
	 s = "x*y";
	 jep.initMultiParse(s);
	 
	 n1 = jep.continueParsing();
	 o1 = jep.evaluate(n1);
	 myAssertEquals("x*y", 30.0,o1);
	 
	 n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);

	 /******/

	 s = "\n";
	 jep.initMultiParse(s);
	 n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);

	 /******/

	 s = "";
	 jep.initMultiParse(s);
	 n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);

	 /******/

	 s = "\n\n\n";
	 jep.initMultiParse(s);
	 n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);

	 /******/

	 s = "\n\n\n1\n\n\n";
	 jep.initMultiParse(s);
	 n1 = jep.continueParsing();
	 o1 = jep.evaluate(n1);
	 myAssertEquals("1", 1.0,o1);
	 n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);

	}


	public void testEmptyEqn() throws Exception
	{
	 String s = "x=5;;y=6;x+y";
	 printTestHeader("Testing reading multiple equations: "+s);
	 jep.initMultiParse(s);

	 Node n1 = jep.continueParsing();
	 Object o1 = jep.evaluate(n1);
	 this.myAssertEquals("x=5", 5.0,o1);

	 Node n2 = jep.continueParsing();
	 Object o2 = jep.evaluate(n2);
	 this.myAssertEquals("y=6", 6.0,o2);

	 Node n3 = jep.continueParsing();
	 Object o3 = jep.evaluate(n3);
	 this.myAssertEquals("x+y", 11.0,o3);

	 Node n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);
	}

	public void testEmptyEqn2() throws Exception
	{
	 String s = "x=5;/*foo*/;y=6;x+y";
	 printTestHeader("Testing reading multiple equations: "+s);
	 jep.initMultiParse(s);

	 Node n1 = jep.continueParsing();
	 Object o1 = jep.evaluate(n1);
	 this.myAssertEquals("x=5", 5.0,o1);

	 Node n2 = jep.continueParsing();
	 Object o2 = jep.evaluate(n2);
	 this.myAssertEquals("y=6", 6.0,o2);

	 Node n3 = jep.continueParsing();
	 Object o3 = jep.evaluate(n3);
	 this.myAssertEquals("x+y", 11.0,o3);

	 Node n4 = jep.continueParsing();
	 assertNull("Next parse should give a null result",n4);
	}

	
	/**
	 * Tests for bug #52
	 */
	public void testExpectedTokenSequence() {
		printTestHeader("Checking whether expected token sequence is set");
		Jep j = new Jep();
		try {
			j.parse("1+");
		} catch (com.singularsys.jep.ParseException e) {
			Throwable t = e.getCause();
			// ensure the cause is set
			assertNotNull(t);
			if (t instanceof com.singularsys.jep.parser.ParseException) {
				com.singularsys.jep.parser.ParseException specialE = 
					(com.singularsys.jep.parser.ParseException)t;
				System.out.println("Contains parser.ParseException :)");
				int expectedTokens[][] = specialE.expectedTokenSequences;
				Assert.assertTrue(expectedTokens.length > 0);
			} else {
				Assert.fail();
			}
		}	
	}
}
