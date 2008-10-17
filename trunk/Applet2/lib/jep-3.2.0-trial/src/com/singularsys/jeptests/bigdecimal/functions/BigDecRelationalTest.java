package com.singularsys.jeptests.bigdecimal.functions;

import java.math.BigDecimal;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.bigdecimal.functions.BigDecRelational;

public class BigDecRelationalTest {
	BigDecRelational eq, ge, gt, le, lt, ne;

	@Before
	public void setUp() throws Exception {
		eq = new BigDecRelational(BigDecRelational.EQ);
		ge = new BigDecRelational(BigDecRelational.GE);
		gt = new BigDecRelational(BigDecRelational.GT);
		le = new BigDecRelational(BigDecRelational.LE);
		lt = new BigDecRelational(BigDecRelational.LT);
		ne = new BigDecRelational(BigDecRelational.NE);
	}

	@Test
	public void alltests() {		
		
		test(BigDecimal.ZERO, eq, BigDecimal.ZERO, true);
		test(BigDecimal.ZERO, ne, BigDecimal.ZERO, false);
		test(BigDecimal.ZERO, lt, BigDecimal.ZERO, false);
		test(BigDecimal.ZERO, gt, BigDecimal.ZERO, false);
		test(BigDecimal.ZERO, le, BigDecimal.ZERO, true);
		test(BigDecimal.ZERO, ge, BigDecimal.ZERO, true);
		
		test(BigDecimal.ZERO, eq, BigDecimal.ONE, false);
		test(BigDecimal.ZERO, ne, BigDecimal.ONE, true);
		test(BigDecimal.ZERO, lt, BigDecimal.ONE, true);
		test(BigDecimal.ZERO, gt, BigDecimal.ONE, false);
		test(BigDecimal.ZERO, le, BigDecimal.ONE, true);
		test(BigDecimal.ZERO, ge, BigDecimal.ONE, false);

		test(BigDecimal.ONE, eq, BigDecimal.ONE, true);
		test(BigDecimal.ONE, ne, BigDecimal.ONE, false);
		test(BigDecimal.ONE, lt, BigDecimal.ONE, false);
		test(BigDecimal.ONE, gt, BigDecimal.ONE, false);
		test(BigDecimal.ONE, le, BigDecimal.ONE, true);		
		test(BigDecimal.ONE, ge, BigDecimal.ONE, true);
	
		test(BigDecimal.ONE, eq, BigDecimal.ZERO, false);
		test(BigDecimal.ONE, ne, BigDecimal.ZERO, true);
		test(BigDecimal.ONE, lt, BigDecimal.ZERO, false);
		test(BigDecimal.ONE, gt, BigDecimal.ZERO, true);
		test(BigDecimal.ONE, le, BigDecimal.ZERO, false);		
		test(BigDecimal.ONE, ge, BigDecimal.ZERO, true);
	}

	/**
	 * Performs test on the class with a set operator type. Checks whether
	 * application of the operator to <code>v1</code> and <code>v2</code>
	 * results in <code>result</code>.
	 */
	private void test(BigDecimal v1, BigDecRelational pfmc, BigDecimal v2, boolean result) {
		Stack<Object> st = new Stack<Object>();
		
		// add the values
		st.add(v1);
		st.add(v2);
		
		try {
			// run the operator
			pfmc.run(st);
			// if result is more than one element, fail
			if (st.size() != 1) Assert.fail("More than one return value on stack");
			// get return value
			Object value = st.pop();
			// get 
			if (!(value instanceof Boolean)) Assert.fail("Return value is not Boolean");
			// check if value is correct
			if ((Boolean)value != Boolean.valueOf(result)) Assert.fail("Evaluated as "+value+" but should be " + result);
		} catch (EvaluationException e) {
			Assert.fail("Unexpected exception occurred: " + e.getMessage() + "\n" + e.getStackTrace());
		}
		
	}
}
