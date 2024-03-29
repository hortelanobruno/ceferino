
package org.nfunk.jeptesting;

import java.io.*;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.nfunk.jep.*;

import com.singularsys.jep.VariableTable;
import com.singularsys.jep.standard.Complex;

/**
 * This class is designed for testing the validity of JEP evaluations.
 * Expressions from a text file are evaluated with JEP in pairs of two, and
 * the results are compared. If they do not match, the two expressions are 
 * printed to standard output.<p>
 * Take for example an input text file containing the two lines
 * <pre>1+2
 *3.</pre>
 * The expressions '1+2' and '3' are evaluated with JEP and the results compared.
 * 
 * @author Nathan Funk
 */
public class ExpressionEvalTest extends TestCase {

	/** The parser */
	JEP jep;
	
	/** Current line position */
	int lineCount;

	/**
	 * Constructor
	 *
	public JEPTester() {
		// Set up the parser
		jep = new JEP();
		jep.setImplicitMul(true);
		jep.addStandardFunctions();
		jep.addStandardConstants();
		jep.addComplex();
		jep.setTraverse(false);
		lineCount = 0;
	}*/
	
	/**
	 * Creates a new JEPTest instance
	 */
	public ExpressionEvalTest(String name) {
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
		jep.addVariable("true", Boolean.TRUE);
		jep.addVariable("false", Boolean.FALSE);
		lineCount = 0;
	}
	
	
	/**
	 * The main method checks the arguments and creates an instance
	 * and calls it's run method.
	 */
	public static void main(String args[]) {
		String fileName;
		
		// get filename from argument, or use default
		if (args!=null && args.length>0) {
			fileName = args[0];
		} else {
			fileName = "JEPTestExpressions.txt";
			println("Using default input file: " + fileName);
		}
		
		// Create an instance of this class and analyse the file
		ExpressionEvalTest jt = new ExpressionEvalTest("JEP Test");
		jt.setUp();
		jt.testWithFile(fileName);
	}

	public void testWithFile() {
		testWithFile("JEPTestExpressions.txt");
	}
	
	/**
	 * Loads the file specified in fileName. Evaluates the expressions listed
	 * in it and compares the expressions with the results.
	 */
	public void testWithFile(String fileName) {
		BufferedReader reader;
		Object v1, v2;
		String expression1, expression2;
		boolean hasError = false;

		// Load the input file
		try {
			reader = new BufferedReader(new FileReader(fileName));
			println("Reading from "+fileName);
		} catch (Exception e) {
			println("File \""+fileName+"\" not found");

			Assert.fail("File \""+fileName+"\" not found");
			return;
		}
		
		// reset the line count
		lineCount = 0;
		
		// cycle through the expressions in pairs of two
		println("Evaluating and comparing expressions...");
		while (true) {
			v1 = null;
			v2 = null;
			
			// get values of a pair of two lines
			try {
				expression1 = getNextLine(reader);
				expression2 = getNextLine(reader);
				if (expression1 != null && expression2 != null) {
					System.out.println("Checking \"" + expression1 + "\" == \"" + expression2 + "\"?");
					v1 = parseNextLine(expression1);
					v2 = parseNextLine(expression2);
				}
			} catch (Exception e) {
				println("Exception occured: "+e.getMessage());
				e.printStackTrace();
				hasError = true;
				break;
			}

			// expression1 or expression2 is null when end of file is reached
			if (expression1 == null || expression2 == null) {
				println("Reached end of file.");
				break;
			}
	
			// compare the results
			if (!equal(v1, v2)) {
				hasError = true;
				print("Line: " + lineCount + ": ");
				println("\"" + expression1 + "\" (" + v1.toString() + ") != \"" 
						    + expression2 + "\" (" + v2.toString()+")");
			}
		}
		
		// Closing remarks
		print("\n" + lineCount + " lines processed. ");
		if (hasError) {
			print("Errors were found.\n\n");
		} else {
			print("No errors were found.\n\n");
		}
		
		// Fail if errors are found
		Assert.assertTrue("Errors were found", !hasError);
	}
	
	/**
	 * Reads the next line from the Reader into a  String.
	 * @throws Exception when IOException occurs, parsing fails, or when
	 *         evaluation fails
	 */
	private String getNextLine(BufferedReader reader) throws Exception {
		String line;
		
		// cycle till a valid line is found
		do {
			line = reader.readLine(); // returns null on end of file
			if (line == null) return null;
			lineCount++;
		} while (line.length() == 0 || line.trim().charAt(0) == '#');
		
		return line;
	}
	
	/**
	 * Parses a single line from the reader, and returns the
	 * evaluation of that line.
	 * @return the value of the evaluated line. Returns null when the end of the file
	 *         is reached.
	 * @throws Exception when parsing fails, or when
	 *         evaluation fails
	 */
	private Object parseNextLine(String line) throws Exception {
		Object value;
		String errorStr;
		// parse the expression
		jep.parseExpression(line);
		// did an error occur while parsing?
		if (jep.hasError()) {
			errorStr = jep.getErrorInfo();
			throw new Exception("Error while parsing line " + lineCount + ": " + errorStr);
		}
		
		// evaluate the expression
		value = jep.getValueAsObject();
		// did an error occur while evaluating?
		if (value == null || jep.hasError()) {
			errorStr = jep.getErrorInfo();
			throw new Exception("Error while evaluating line " + lineCount + ": " + errorStr);
		}
			
		return value;
	}

	/**
	 * Compares o1 and o2. Copied from Comparative.java.
	 * @return true if o1 and o2 are equal. false otherwise.
	 */
	private boolean equal(Object param1, Object param2)
	{
		double tolerance = 1e-15;
		if ((param1 instanceof Complex) && (param2 instanceof Complex)) {
			return ((Complex)param1).equals((Complex)param2, tolerance);
		}
		if ((param1 instanceof Complex) && (param2 instanceof Number)) {
			return ((Complex)param1).equals(new Complex((Number) param2), tolerance);
		}
		if ((param2 instanceof Complex) && (param1 instanceof Number)) {
			return ((Complex)param2).equals(new Complex((Number) param1), tolerance);
		}
		if ((param1 instanceof Number) && (param2 instanceof Number)) {
			return Math.abs(((Number)param1).doubleValue()-((Number)param2).doubleValue())
					< tolerance;
		}
		// test any other types here
		return param1.equals(param2);
	}


	
	/**
	 * Helper function for printing.
	 */
	private static void print(String str) {
		System.out.print(str);
	}

	/**
	 * Helper function for printing lines.
	 */
	private static void println(String str) {
		System.out.println(str);
	}
}
