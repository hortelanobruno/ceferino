package org.nfunk.jeptesting;

import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.functions.Logarithm;

import junit.framework.Assert;
import junit.framework.TestCase;

public class LogarithmTest extends TestCase {

	public LogarithmTest(String name) {
		super(name);
	}

	/**
	 * Test method for 'org.nfunk.jep.function.Logarithm.run(Stack)'
	 * Tests the return value of log(NaN). This is a test for bug #1177557
	 */
	public void testLogarithm() {
		Logarithm logFunction = new Logarithm();
		java.util.Stack<Object> stack = new java.util.Stack<Object>();
		stack.push(new Double(Double.NaN));
		try {
			logFunction.run(stack);
		} catch (EvaluationException e) {
			Assert.fail();
		}
		Object returnValue = stack.pop();

		if (returnValue instanceof Double) {
			Assert.assertTrue(Double.isNaN(((Double)returnValue).doubleValue()));
		} else {
			Assert.fail();
		}
	}

}
