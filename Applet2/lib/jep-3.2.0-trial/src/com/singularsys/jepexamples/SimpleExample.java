package com.singularsys.jepexamples;

import com.singularsys.jep.Jep;

/**
 * A simple example that demonstrates the use of JEP for evaluation of a single
 * expression.
 * @author Singular Systems
 */
public class SimpleExample {
	public static void main(String[] args) {

		// Create a new Jep instance
		Jep jep = new Jep();
		// Add the variable x to the parser and initialize it's value to 10
		jep.addVariable("x", 10);
		// Try parsing and evaluating an example expression
		try {
			jep.parse("x+1");
			Object result = jep.evaluate();
			
			// If the evaluation succeeds, the result will be printed here
			System.out.println("x + 1 = " + result);

		} catch (Exception e) {
			// If an exception is thrown while parsing or evaluating
			// information about the error is printed here
			System.out.println("An error occured: " + e.getMessage());
		}
	}
}
